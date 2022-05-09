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
import entities.Entity;
import resources.Server;
import utils.CustomRandomizer;
import utils.Distributions;
import resources.HeavyAirstrip;
import resources.LightAirstrip;
import resources.MidAirstrip;
import events.ArrivalEvent;
import events.Event;
import resources.CustomQueue;
import events.StopExecutionEvent;
import policies.ServerSelectionPolicy;
import resources.Queue;

/**
 * Event oriented simulation of an airport
 */
public class AirportSimulation implements Engine {
    private String report = "==============================================================================================\n"
            + "                                        R E P O R T                                           \n" +
            "==============================================================================================\n\n";
    private double endTime;
    private FutureEventList fel;
    private List<Server>[]servers;

    /**
     * Creates the execution engine for the airport simulator.
     *
     * @param airstripQuantity The number of airstrips (servers).
     * @param endTime          The amount of time the simulator will simulate (run
     *                         length).
     * @param policy           The object that defines the airstrip selection policy
     *                         each time an arrival occurs.
     */
    //TODO
    public AirportSimulation(int airstripQuantity, double endTime, ServerSelectionPolicy policy, long seed) {
        // Option for simulation with a especific seed
        if (seed != 0) {
            CustomRandomizer.setSeed(seed);
        }

        this.endTime = endTime;
        this.fel = new FutureEventList();
        this.servers = new List[3];

        for (int i = 0; i < servers.length; i++) {
            servers[i] = new ArrayList<Server>();
        }

        for (int i = 0; i < 3; i++) {
            Queue queue = new CustomQueue();
            this.servers[0].add(new LightAirstrip(queue));
            queue.setAssignedServer(servers[0].get(i));
        }

        for (int i = 3; i < 7; i++) {
            Queue queue = new CustomQueue();
            this.servers[1].add(new MidAirstrip(queue));
            queue.setAssignedServer(servers[1].get(i));
        }

        for (int i = 7; i < 9; i++) {
            Queue queue = new CustomQueue();
            this.servers[2].add(new HeavyAirstrip(queue));
            queue.setAssignedServer(servers[2].get(i));
        }

        this.fel.insert(new StopExecutionEvent(endTime));
        this.fel.insert(new ArrivalEvent(0, new LightAircraft(policy.selectServer(servers, 1)), policy));
        this.fel.insert(new ArrivalEvent(0, new MidAircraft(policy.selectServer(servers, 2)), policy));
        this.fel.insert(new ArrivalEvent(0, new HeavyAircraft(policy.selectServer(servers, 3)), policy));
        this.fel.insert(new ArrivalEvent(5*1440, new Maintenance(policy.selectServer(servers, 1)), policy));

    }

    @Override
    public void execute() {
        Event event;

        while (!((event = fel.getImminent()) instanceof StopExecutionEvent)) {
            event.planificate(servers, fel);
        }
    }

    //TODO
    /*
    @Override
    public void generateReport() {
        int inQueueAircrafts = 0;

        for (int i= 0; i<9; i++){
            for (Server server : servers[i]) {
                inQueueAircrafts += server.getQueue().size();
            }
        }
        int landings = Entity.getIdCount() - inQueueAircrafts - 1;

        DecimalFormat format = new DecimalFormat("#0.00"), dformat = new DecimalFormat("#0.00%");

        report += "Cantidad total de aterrizajes: " + landings + "\n" +
                "Tiempo total de espera en cola: " + Entity.getTotalWaitingTime() + "\n" +
                "Tiempo medio de espera en cola: " + format.format((double) Entity.getTotalWaitingTime() / landings)
                + "\n" +
                "Tiempo máximo de espera en cola: " + Entity.getMaxWaitingTime() + "\n" +
                "Tiempo total de transito: " + Entity.getTotalTransitTime() + "\n" +
                "Tiempo medio de transito: " + format.format((double) Entity.getTotalTransitTime() / landings) + "\n" +
                "Tiempo máximo de transito: " + Entity.getMaxTransitTime() + "\n";

        report += "Tiempo total de ocio:" + "\n";
        for (Server server : servers) {
            report += "    Server " + server.getId() + ": " + server.getIdleTime() + "\n";
        }

        report += "Tiempo máximo de ocio: " + "\n";
        for (Server server : servers) {
            report += "    Server " + server.getId() + ": " + server.getMaxIdleTime() + "\n";
        }

        report += "Porcentaje de ocio respecto al total de tiempo:" + "\n";
        for (Server server : servers) {
            report += "    Server " + server.getId() + ": "
                    + dformat.format((server.getIdleTime()) / (double) this.getEndTime()) + "\n";
        }

        report += "Porcentaje del maximo de ocio respecto al tiempo total de ocio:" + "\n";
        for (Server server : servers) {
            report += "    Server " + server.getId() + ": "
                    + dformat.format((server.getMaxIdleTime()) / (double) server.getIdleTime()) + "\n";
        }

        report += "Tamaño máximo de la cola de espera: " + "\n";
        for (Server server : servers) {
            report += "    Server " + server.getId() + ": " + server.getQueue().getMaxSize() + "\n";
        }

        report += "Semilla utilizada: " + CustomRandomizer.getSeed() + "\n";
    }

    */
    
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

    public List<Server>[] getServers() {
        return this.servers;
    }

    @Override
    public void generateReport() {
        // TODO borrar
    }
}