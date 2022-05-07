package behaviors;

import events.Event;
import entities.HeavyAircraft;
import entities.Entity;
import utils.Randomizer;
import events.ArrivalEvent;
import utils.CustomRandomizer;
import utils.Distributions;

public class HeavyArrivalEventBehavior extends EventBehavior {
    private static HeavyArrivalEventBehavior heavyArrivalEventBehavior;
    private final int average[] = { 60, 30 };
    private final int desviation = 2;

    private HeavyArrivalEventBehavior(Randomizer randomizer) {
        super(randomizer);
    }

    public static HeavyArrivalEventBehavior getInstance() {
        if (HeavyArrivalEventBehavior.heavyArrivalEventBehavior == null)
            HeavyArrivalEventBehavior.heavyArrivalEventBehavior = new HeavyArrivalEventBehavior(
                    CustomRandomizer.getInstance());

        return heavyArrivalEventBehavior;
    }

    @Override
    public Event nextEvent(Event actualEvent, Entity entity) {
        int clock = 0;

        if ((actualEvent.getClock() % 720) >= 7 && (actualEvent.getClock() % 720) <= 10) {
            clock = (int) (Distributions.normal(average[1], desviation, super.getRandomizer()));
        } else {
            clock = (int) Distributions.normal(average[0], desviation, super.getRandomizer());
        }

        return new ArrivalEvent(actualEvent.getClock() + clock, new HeavyAircraft(entity.getAttendingServer()),
                ((ArrivalEvent) actualEvent).getSelectionPolicy());
    }
}
