package behaviors;

import events.Event;
import entities.MidAircraft;
import entities.Entity;
import utils.Randomizer;
import events.ArrivalEvent;
import utils.CustomRandomizer;
import utils.Distributions;

public class MidArrivalEventBehavior extends EventBehavior {
    private static MidArrivalEventBehavior midArrivalEventBehavior; 
    private final int lambda[] = {30,15};

    private MidArrivalEventBehavior(Randomizer randomizer) {
        super(randomizer);
    }

    public static MidArrivalEventBehavior getInstance() {
        if (MidArrivalEventBehavior.midArrivalEventBehavior == null)
        MidArrivalEventBehavior.midArrivalEventBehavior = new MidArrivalEventBehavior(
                    CustomRandomizer.getInstance());

        return midArrivalEventBehavior;
    }

    @Override
    public Event nextEvent(Event actualEvent, Entity entity) {
        int clock = 0;

        if ((actualEvent.getClock() % 720) >= 7 && (actualEvent.getClock() % 720) <= 10) {
            clock = (int) (Distributions.exponencial(lambda[1], super.getRandomizer()));
        } else {
            clock = (int) Distributions.exponencial(lambda[0], super.getRandomizer());
        }

        return new ArrivalEvent(actualEvent.getClock() + clock, new MidAircraft(entity.getAttendingServer()),
                ((ArrivalEvent) actualEvent).getSelectionPolicy());
    }
}
