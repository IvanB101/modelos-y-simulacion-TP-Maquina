package engine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.management.InvalidAttributeValueException;

import entities.HeavyAircraft;
import entities.LightAircraft;
import entities.Maintenance;
import entities.MidAircraft;
import events.ArrivalEvent;
import events.Event;
import events.StopExecutionEvent;
import policies.ServerSelectionPolicy;
import resources.CustomQueue;
import resources.HeavyAirstrip;
import resources.LightAirstrip;
import resources.MidAirstrip;
import resources.Server;
import utils.CustomRandomizer;
import utils.Statistics;

/**
 * Event oriented simulation of an airport
 */
public class AirportSimulation implements Engine {
    private String report = "=========================================================================================================================================================\n"
            + "                                                                        R E P O R T                                           \n" +
            "=========================================================================================================================================================\n\n";
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
     * @param seed          seed for the generation of random numbers
     * @param alfa          value for the confidence interavals when doing
     *                      replications, the is a (1-alfa) chance for the interval
     *                      to contain the poblational statistic
     * @throws InvalidAttributeValueException
     */
    public AirportSimulation(int[] configuration, double endTime, ServerSelectionPolicy policy, long seed)
            throws InvalidAttributeValueException {
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
        // First Maintenance will be in the fifth day whcich is its average
        this.fel.insert(new ArrivalEvent(5*24*60, new Maintenance(statistics), policy));
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
        report += statistics.getEntityData().toString();

        report += statistics.getServerData().toString();

        report += statistics.getServerByTypeData().toString();

        report += "Semilla utilizada: " + CustomRandomizer.getSeed() + "\n";
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