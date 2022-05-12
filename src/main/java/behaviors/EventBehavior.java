package behaviors;

import events.Event;
import utils.Statistics;
import entities.Entity;

public abstract class EventBehavior {
    public EventBehavior() {
    }

    /**
     * This method returns the next event calculated from the current one.
     *
     * @param actualEvent The current event.
     * @param entity      The corresponding entity for the next event.
     * @return The next event.
     */
    public abstract Event nextEvent(Event actualEvent, Entity entity, Statistics statistics);
}