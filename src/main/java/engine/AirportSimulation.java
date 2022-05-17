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
     * @param configuration An array with the number of airstrips (servers) of each
     *                      kind.
     * @param endTime       The amount of time the simulator will simulate (run
     *                      length).
     * @param policy        The object that defines the airstrip selection policy
     *                      each time an arrival occurs.
     */
    public AirportSimulation(int[] configuration, double endTime, ServerSelectionPolicy policy, long seed) {
        // Option for simulation with a especific seed
        if (seed != 0) {
            CustomRandomizer.setSeed(seed);
        }

        this.statistics = new Statistics(servers, configuration, endTime);
        this.endTime = endTime;
        this.fel = new FutureEventList();
        this.servers = new ArrayList<Server>();

        // Inicialization of servers
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

        // Insertions of the first ArrivalEvent with each type of entity
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

        report += statistics.getEntityData().toString();

        report += statistics.getServerData().toString();

        String[]analytics = new String[servers.size() + 1];

        report += "\n\nEstadísticas por Servidor discriminadas por tipo\n\n";

        String[] header2 = {
                "", "Tiempo total de ocio", "Tiempo máximo de ocio", "Porcentaje de tiempo de ocio respecto al total",
                "Porcentaje del maximo de ocio respecto al tiempo total de ocio", "Tamaño máximo de la cola de espera"
        };

        analytics = new String[header2.length];

        String f1 = "%-65s", f2 = "%21s";

        for (int i = 0; i < header2.length; i++) {
            analytics[i] = String.format(f1, header2[i]);
        }

        for (int i = 0; i < statistics.getServerClassesNumber(); i++) {
            analytics[0] += String.format(f2, statistics.getClassServerName(i));
            analytics[1] += String.format(f2, format.format(statistics.getTotalIdleTime(i)));
            analytics[2] += String.format(f2, format.format(statistics.getMaxIdleTime(i)));
            analytics[3] += String.format(f2,
                    dformat.format(statistics.getTotalIdleTime(i) / (endTime * statistics.getServerAmount(i))));
            analytics[4] += String.format(f2,
                    dformat.format(statistics.getMaxIdleTime(i) / statistics.getTotalIdleTime(i)));
            analytics[5] += String.format(f2, statistics.getMaxQueueSize(i));
        }

        report += String.join("\n", analytics);

        report += "\n\n\nSemilla utilizada: " + CustomRandomizer.getSeed() + "\n";
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