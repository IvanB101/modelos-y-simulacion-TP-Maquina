package events;

import java.util.Comparator;
import java.util.List;
import entities.Entity;
import resources.Server;
import engine.FutureEventList;
import behaviors.EventBehavior;

public abstract class Event {
    // attributes
    private int clock;

    private int priority;

    // associations
    private Entity entity;
    private EventBehavior eventBehavior;
    private static Comparator<Event> comparator = new Comparator<Event>() {
        int ret = 0;

        //TODO
        @Override
        public int compare(Event t, Event t1) {
            if (t.getClock() < t1.getClock()) {
                ret = -1;
            } else if (t.getClock() > t1.getClock()) {
                ret = 1;
            } else if (t.getPriority() < t1.getPriority()) {
                ret = -1;
            } else {
                ret = 1;
            }
            return ret;
        }
    };

    // other
    /**
     * Used to format toString output
     */
    protected static int END_TIME_DIGITS;

    public Event(int clock, Entity entity, EventBehavior eventBehavior) {
        this.clock = clock;
        this.entity = entity;
        this.eventBehavior = eventBehavior;
    }

    /**
     * This method performs the necessary planifications that this event
     * has to do for the proper execution of bootstrapping.
     *
     * @param servers The list of servers needed to do the planification.
     * @param fel     The future event list to insert the next events.
     */
    public abstract void planificate(List<Server> servers, FutureEventList fel);

    public int getClock() {
        return clock;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public EventBehavior getEventBehavior() {
        return eventBehavior;
    }

    public void setEventBehavior(EventBehavior eventBehavior) {
        this.eventBehavior = eventBehavior;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public static Comparator<Event> getComparator() {
        return comparator;
    }
}