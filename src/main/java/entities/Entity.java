package entities;

import java.util.List;
import java.util.Comparator;
import events.Event;
import resources.Server;
import events.ArrivalEvent;
import events.EndOfServiceEvent;
import java.util.LinkedList;

public abstract class Entity {
    private static int idCount = 0;
    private static int totalWaitingTime = 0;
    private static int maxWaitingTime = 0;
    private static int totalTransitTime = 0;
    private static int maxTransitTime = 0;

    // attributes
    private int id;
    private int waitingTime;
    // Temporarily used to save service duration
    private int transitTime;

    // associations
    private Server attendingServer;
    private List<Event> events;

    // others
    /**
     * Used if it is necessary to order chronologically the events of this entity.
     */
    private Comparator<Event> comparator = Event.getComparator();

    public Entity(Server server) {
        idCount++;
        this.id = idCount;
        this.waitingTime = 0;
        this.transitTime = 0;
        this.attendingServer = server;
        this.events = new LinkedList<Event>();
    }

    public static void setMaxWaitingTime(int maxWaitingTime) {
        Entity.maxWaitingTime = maxWaitingTime;
    }

    public static void setMaxTransitTime(int maxTransitTime) {
        Entity.maxTransitTime = maxTransitTime;
    }

    public static void accumulateTransitTime(int transitTime) {
        Entity.totalTransitTime += transitTime;
    }

    public static void accumulateWaitingTime(int waitingTime) {
        Entity.totalWaitingTime += waitingTime;
    }

    public static int getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public static int getIdCount() {
        return idCount;
    }

    public static int getMaxWaitingTime() {
        return maxWaitingTime;
    }

    public static int getTotalTransitTime() {
        return totalTransitTime;
    }

    public static int getMaxTransitTime() {
        return maxTransitTime;
    }

    public int getId() {
        return this.id;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTransitTime() {
        return this.transitTime;
    }

    public void setTransitTime(int transitTime) {
        this.transitTime = transitTime;
    }

    public ArrivalEvent getArrivalEvent() {
        return (ArrivalEvent) events.get(0);
    }

    public EndOfServiceEvent getEndOfServiceEvent() {
        return (EndOfServiceEvent) events.get(1);
    }

    public Server getAttendingServer() {
        return attendingServer;
    }

    public void setAttendingServer(Server attendingServer) {
        this.attendingServer = attendingServer;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvent(Event event) {
        events.add(event);
        events.sort(comparator);
    }
}