package behaviors;

import events.Event;
import entities.Entity;
import utils.Randomizer;

public abstract class EventBehavior {
    private Randomizer randomizer;

    public EventBehavior(Randomizer randomizer) {
        this.randomizer = randomizer;
    }

    public Randomizer getRandomizer() {
        return randomizer;
    }

    /**
     * This method returns the next event calculated from the current one.
     *
     * @param actualEvent The current event.
     * @param entity      The corresponding entity for the next event.
     * @return The next event.
     */
    public abstract Event nextEvent(Event actualEvent, Entity entity);
}