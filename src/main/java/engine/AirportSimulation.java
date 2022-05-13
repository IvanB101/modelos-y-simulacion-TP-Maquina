package engine;

import entities.HeavyAircraft;
import entities.LightAircraft;
import entities.Maintenance;
import entities.MidAircraft;

import java.util.List;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.BufferedWriter;
import resources.Server;
import utils.CustomRandomizer;
import utils.Statistics;
import resources.HeavyAirstrip;
import resources.LightAirstrip;
import resources.MidAirstrip;
import events.ArrivalEvent;
import events.Event;
import resources.CustomQueue;
import events.StopExecutionEvent;
import policies.ServerSelectionPolicy;

/**
 * Event oriented simulation of an airport
 */
public class AirportSimulation implements Engine {
    private String report = "==============================================================================================\n"
            + "                                        R E P O R T                                           \n" +
            "==============================================================================================\n\n";
    private double endTime;
    private FutureEventList fel;
    private List<Server> servers;
    private Statistics statistics;

    /**
     * Creates the execution engine for the airport simulator.
     *
     * @param airstripQuantity The number of airstrips (servers).
     * @param endTime          The amount of time the simulator will simulate (run
     *                         length).
     * @param policy           The object that defines the airstrip selection policy
     *                         each time an arrival occurs.
     */
    public AirportSimulation(int[] airstripQuantity, double endTime, ServerSelectionPolicy policy, long seed) {
        // Option for simulation with a especific seed
        if (seed != 0) {
            CustomRandomizer.setSeed(seed);
        }

        this.statistics = new Statistics(servers);
        statistics.setServerAmounts(airstripQuantity);
        this.endTime = endTime;
        this.fel = new FutureEventList();
        this.servers = new ArrayList<Server>();

        for (int i = 0; i < statistics.getServerAmount(LightAircraft.getClassId()); i++) {
            servers.add(new LightAirstrip(new CustomQueue(), statistics));
        }
        for (int i = 0; i < statistics.getServerAmount(MidAircraft.getClassId()); i++) {
            servers.add(new MidAirstrip(new CustomQueue(), statistics));
        }
        for (int i = 0; i < statistics.getServerAmount(HeavyAircraft.getClassId()); i++) {
            servers.add(new HeavyAirstrip(new CustomQueue(), statistics));
        }

        this.statistics.setServers(servers);

        this.fel.insert(new StopExecutionEvent(endTime));
        this.fel.insert(new ArrivalEvent(0, new LightAircraft(statistics), policy));
        this.fel.insert(new ArrivalEvent(0, new MidAircraft(statistics), policy));
        this.fel.insert(new ArrivalEvent(0, new HeavyAircraft(statistics), policy));
        this.fel.insert(new ArrivalEvent(0, new Maintenance(statistics), policy));
    }

    @Override
    public void execute() {
        Event event;

        while (!((event = fel.getImminent()) instanceof StopExecutionEvent)) {
            event.planificate(servers, fel, statistics);
        }
    }

    @Override
    public void generateReport() {
        DecimalFormat format = new DecimalFormat("#0.00"), dformat = new DecimalFormat("#0.00%");

        report += "Estadísticas disciminadas por tipo de Entidad:\n\n";

        String[] analytics = { "                                   ", "Cantidad total de aterrizajes:     ",
                "Tiempo total de espera en cola:    ",
                "Tiempo medio de espera en cola:    ", "Tiempo máximo de espera en cola:   ",
                "Tiempo medio de transito:          ", "Tiempo máximo de transito:         " };

        for (int i = 0; i < statistics.getEntityClassesNumber(); i++) {
            int landings = (statistics.getEntityIdCount(i) - statistics.getInQueueAircrafts(i));

            analytics[0] += String.format("%-21s", statistics.getClassEntityName(i));
            analytics[1] += String.format("%-21s", format.format(landings));
            analytics[2] += String.format("%-21s", format.format(statistics.getTotalWaitingTime(i)));
            analytics[3] += String.format("%-21s", format.format(statistics.getTotalWaitingTime(i) / landings));
            analytics[4] += String.format("%-21s", format.format(statistics.getMaxWaitingTime(i)));
            analytics[5] += String.format("%-21s", format.format(statistics.getTotalTransitTime(i) / landings));
            analytics[6] += String.format("%-21s", format.format(statistics.getMaxTransitTime(i)));
        }
        report += String.join("\n", analytics);

        /*
         * report += "\n\n\nEstadísticas por Servidor según Id\n\n";
         * 
         * String[]analytics2 = { "\n\n          ", "\n\nTipo ",
         * "\n\nTiempo total de ocio  ", "\n\nTiempo máximo de ocio ",
         * "Porcentaje de tiempo  \nde ocio ocio respecto \nal total: ",
         * "Porcentaje del maximo \nde ocio respecto al    \ntiempo total de ocio:",
         * "\nTamaño máximo de la \ncola de espera:" };
         * for (int i = 0; i < statistics.getServerAmount(0); i++) {
         * Server server = servers.get(i);
         * 
         * analytics2[0] += String.format("%-10s", "Server" + servers.get(i).getId());
         * analytics2[1] += String.format("%-21s",
         * statistics.getClassServerName(server.getClassServerid()));
         * analytics2[2] += String.format("%-21s", server.getIdleTime());
         * analytics2[3] += String.format("%-21s", server.getMaxIdleTime());
         * analytics2[4] += String.format("%-21s",
         * server.getMaxIdleTime()/server.getIdleTime());
         * analytics2[5] += String.format("%-21s", server.getMaxIdleTime()/endTime);
         * analytics2[6] += String.format("%-21s", server.getQueue().getMaxSize());
         * }
         * report += String.join("\n", analytics2);
         */
        for (Server server : servers) {
            report += "\n\nServer " + server.getId() + "   " + statistics.getClassServerName(server.getClassServerid())
                    + "\n" +
                    "Tiempo total de ocio: " + format.format(server.getIdleTime()) + "\n" +
                    "Tiempo máximo de ocio: " + format.format(server.getMaxIdleTime()) + "\n" +
                    "Porcentaje de tiempo de ocio respecto al total: "
                    + dformat.format(server.getIdleTime() / endTime) + "\n" +
                    "Porcentaje del maximo de ocio respecto al tiempo total de ocio:"
                    + dformat.format(server.getMaxIdleTime() / server.getIdleTime()) + "\n" +
                    "Tamaño máximo de la cola de espera: " + server.getQueue().getMaxSize() + "\n" +
                    "Durabilidad: " + server.getDurability() + "\n\n";
        }

        report += "\nEstadísticas por Servidor discriminadas por tipo\n\n";
        for (int i = 0; i < statistics.getServerClassesNumber(); i++) {
            report += statistics.getClassServerName(i) + "\n" +
                    "Tiempo total de ocio: " + format.format(statistics.getTotalIdleTime(i)) + "\n" +
                    "Tiempo máximo de ocio: " + format.format(statistics.getMaxIdleTime(i)) + "\n" +
                    "Porcentaje de tiempo de ocio ocio respecto al total: "
                    + dformat.format(statistics.getTotalIdleTime(i) / (endTime * statistics.getServerAmount(i))) + "\n"
                    +
                    "Porcentaje del maximo de ocio respecto al tiempo total de ocio:"
                    + dformat.format(statistics.getMaxIdleTime(i) / statistics.getTotalIdleTime(i)) + "\n" +
                    "Tamaño máximo de la cola de espera: " + statistics.getMaxQueueSize(i) + "\n\n";
        }

        report += "\nSemilla utilizada: " + CustomRandomizer.getSeed() + "\n";
    }

    @Override
    public void saveReport() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SS");

        String fecha = format.format(LocalDateTime.now());

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("reports/" + fecha + ".txt"));
            writer.write(report);
            writer.close();
        } catch (Exception exception) {
            System.err.println(exception);
        }
    }

    @Override
    public void showReport() {
        System.out.println(report);
    }

    public double getEndTime() {
        return endTime;
    }

    public List<Server> getServers() {
        return this.servers;
    }

    public Statistics getStatistics() {
        return statistics;
    }
}