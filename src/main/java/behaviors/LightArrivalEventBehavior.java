package behaviors;

import events.Event;
import entities.LightAircraft;
import entities.Entity;
import utils.Randomizer;
import events.ArrivalEvent;
import utils.CustomRandomizer;
import utils.Distributions;

public class LightArrivalEventBehavior extends EventBehavior {
    private static LightArrivalEventBehavior lightArrivalEventBehavior;
    private final int lambda[] = { 40, 20 };

    private LightArrivalEventBehavior(Randomizer randomizer) {
        super(randomizer);
    }

    public static LightArrivalEventBehavior getInstance() {
        if (LightArrivalEventBehavior.lightArrivalEventBehavior == null)
            LightArrivalEventBehavior.lightArrivalEventBehavior = new LightArrivalEventBehavior(
                    CustomRandomizer.getInstance());

        return lightArrivalEventBehavior;
    }

    @Override
    public Event nextEvent(Event actualEvent, Entity entity) {
        int clock = 0;

        if ((actualEvent.getClock() % 720) >= 7 && (actualEvent.getClock() % 720) <= 10) {
            clock = (int) (Distributions.exponencial(lambda[1], super.getRandomizer()));
        } else {
            clock = (int) Distributions.exponencial(lambda[0], super.getRandomizer());
        }

        return new ArrivalEvent(actualEvent.getClock() + clock, new LightAircraft(entity.getAttendingServer()),
                ((ArrivalEvent) actualEvent).getSelectionPolicy());
    }
}
