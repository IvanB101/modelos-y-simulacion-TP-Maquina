package behaviors;

import events.Event;
import entities.HeavyAircraft;
import entities.Entity;
import utils.Randomizer;
import events.ArrivalEvent;
import utils.CustomRandomizer;

public class ArrivalEventBehavior extends EventBehavior {
    private static ArrivalEventBehavior arrivalEventBehavior;

    private ArrivalEventBehavior(Randomizer randomizer) {
        super(randomizer);
    }

    public static ArrivalEventBehavior getInstance() {
        if (ArrivalEventBehavior.arrivalEventBehavior == null)
            ArrivalEventBehavior.arrivalEventBehavior = new ArrivalEventBehavior(CustomRandomizer.getInstance());

        return arrivalEventBehavior;
    }

    @Override
    public Event nextEvent(Event actualEvent, Entity entity) {
        int clock;

        double random = this.getRandomizer().nextRandom();

        if (random < 0.3) {
            clock = 10;
        } else if (random < 0.7) {
            clock = 15;
        } else {
            clock = 20;
        }
        return new ArrivalEvent(actualEvent.getClock() + clock, new HeavyAircraft(entity.getAttendingServer()), ((ArrivalEvent) actualEvent).getSelectionPolicy());
    }
}