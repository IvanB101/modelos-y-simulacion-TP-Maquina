package behaviors;

import events.Event;
import entities.Entity;
import utils.Randomizer;
import utils.CustomRandomizer;
import events.EndOfServiceEvent;

public class EndOfServiceEventBehavior extends EventBehavior {
    private static EndOfServiceEventBehavior endOfServiceEventBehavior;

    private EndOfServiceEventBehavior(Randomizer randomizer) {
        super(randomizer);
    }

    public static EndOfServiceEventBehavior getInstance() {
        if (EndOfServiceEventBehavior.endOfServiceEventBehavior == null)
            EndOfServiceEventBehavior.endOfServiceEventBehavior = new EndOfServiceEventBehavior(
                    CustomRandomizer.getInstance());

        return endOfServiceEventBehavior;
    }

    @Override
    public Event nextEvent(Event actualEvent, Entity entity) {
        int clock;

        double random = this.getRandomizer().nextRandom();

        if (random < 0.1) {
            clock = 8;
        } else if (random < 0.48) {
            clock = 10;
        } else if (random < 0.8) {
            clock = 15;
        } else {
            clock = 20;
        }
        Event event = new EndOfServiceEvent(actualEvent.getClock() + clock, entity);
        entity.setTransitTime(clock);

        return event;
    }
}