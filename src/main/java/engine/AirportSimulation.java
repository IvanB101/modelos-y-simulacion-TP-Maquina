package engine;

import entities.Aircraft;
import java.util.List;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.BufferedWriter;
import entities.Entity;
import resources.Server;
import resources.Airstrip;
import events.ArrivalEvent;
import events.EndOfServiceEvent;
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
    private int endTime;
    private FutureEventList fel;
    private List<Server> servers;

    /**
     * Creates the execution engine for the airport simulator.
     *
     * @param airstripQuantity The number of airstrips (servers).
     * @param endTime          The amount of time the simulator will simulate (run
     *                         length).
     * @param policy           The object that defines the airstrip selection policy
     *                         each time an arrival occurs.
     */
    public AirportSimulation(int airstripQuantity, int endTime, ServerSelectionPolicy policy) {
        this.endTime = endTime;
        this.fel = new FutureEventList();
        this.servers = new ArrayList<Server>();
        for (int i = 0; i < airstripQuantity; i++) {
            Queue queue = new CustomQueue();
            this.servers.add(new Airstrip(queue));
            queue.setAssignedServer(servers.get(i));
        }
        this.fel.insert(new ArrivalEvent(0, new Aircraft(policy.selectServer(servers)), policy));
    }

    // This is to avoid null pointer exception in the constructor
    public void iniciatilize() {
        this.fel.insert(new StopExecutionEvent(endTime, this));
    }

    @Override
    public void execute() {
        this.iniciatilize();
        Event event;

        while (!((event = fel.getImminent()) instanceof StopExecutionEvent)) {
            Entity entity = event.getEntity();
            entity.setEvent(event);
            Server server = entity.getAttendingServer();
            event.planificate(servers, fel);

            if (event instanceof ArrivalEvent) {
                if (server.isBusy() && server.getQueue().isEmpty()) {
                    server.setIdleTimeFinishMark(event.getClock());
                }
            } else if (event instanceof EndOfServiceEvent) {
                if (!server.isBusy()) {
                    server.setIdleTimeStartMark(event.getClock());
                }

                int wait = entity.getEndOfServiceEvent().getClock() - entity.getArrivalEvent().getClock()
                        - entity.getTransitTime();

                int transit = entity.getEndOfServiceEvent().getClock() - entity.getArrivalEvent().getClock();

                entity.setTransitTime(transit);

                entity.setWaitingTime(wait);

                if (Entity.getMaxTransitTime() < transit) {
                    Entity.setMaxTransitTime(transit);
                }

                if (Entity.getMaxWaitingTime() < wait) {
                    Entity.setMaxWaitingTime(wait);
                }

                Entity.accumulateTransitTime(transit);

                Entity.accumulateWaitingTime(wait);
            }
        }
    }

    @Override
    public void generateReport() {
        int inQueueAircrafts = 0;
        for (Server server : servers) {
            inQueueAircrafts += server.getQueue().size();
        }
        // The -1 is to acount for the last arrival event, which isn't processed
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
            report += "    Server " + server.getId() + ":" + server.getIdleTime() + "\n";
        }

        report += "Tiempo máximo de ocio: " + "\n";
        for (Server server : servers) {
            report += "    Server " + server.getId() + ":" + server.getMaxIdleTime() + "\n";
        }

        report += "Porcentaje de ocio respecto al total de tiempo:" + "\n";
        for (Server server : servers) {
            report += "    Server " + server.getId() + ":"
                    + dformat.format((server.getIdleTime()) / (double) this.getEndTime()) + "\n";
        }

        report += "Porcentaje del maximo de ocio respecto al tiempo total de ocio:" + "\n";
        for (Server server : servers) {
            report += "    Server " + server.getId() + ":"
                    + dformat.format((server.getMaxIdleTime()) / (double) server.getIdleTime()) + "\n";
        }

        report += "Tamaño máximo de la cola de espera: " + "\n";
        for (Server server : servers) {
            report += "    Server " + server.getId() + ":" + server.getQueue().getMaxSize() + "\n";
        }
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
            System.out.println("Error when trying to write the report into a file.");
            System.out.println("Showing on screen...");
            System.out.println(report);
        }
    }

    @Override
    public void showReport() {
        System.out.println(report);
    }

    public int getEndTime() {
        return endTime;
    }

    public List<Server> getServers() {
        return this.servers;
    }
}