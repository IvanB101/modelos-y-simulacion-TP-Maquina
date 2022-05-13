package behaviors;

import entities.Entity;
import entities.HeavyAircraft;
import entities.LightAircraft;
import entities.Maintenance;
import entities.MidAircraft;
import events.ArrivalEvent;
import events.Event;
import utils.CustomRandomizer;
import utils.Distributions;
import utils.Randomizer;
import utils.Statistics;

public class ArrivalEventBehavior extends EventBehavior {
    private static ArrivalEventBehavior arrivalEventBehavior;

    private final double averageHeavy[] = { 60, 30 };
    private final double desviationHeavy = 2;
    private final double lambdaLight[] = { 1.0 / 40, 1.0 / 20 };
    private final double lambdaMid[] = { 1.0 / 30, 1.0 / 15 };
    private final double averageMaintenance = 5 * 24 * 60;
    private final double desviationMaintenance = 0.5 * 24 * 60;

    private ArrivalEventBehavior(Randomizer randomizer) {
        super();
    }

    public static ArrivalEventBehavior getInstance() {
        if (ArrivalEventBehavior.arrivalEventBehavior == null)
            ArrivalEventBehavior.arrivalEventBehavior = new ArrivalEventBehavior(CustomRandomizer.getInstance());

        return arrivalEventBehavior;
    }

    @Override
    public Event nextEvent(Event actualEvent, Entity entity, Statistics statistics) {
        double clock;
        Entity nextEntity;

        // Changes the parameter in rush hour for the distributions
        int index = 0;

        if ((actualEvent.getClock() % 720) >= 7 * 60 && (actualEvent.getClock() % 720) <= 10 * 60) {
            index = 1;
        }

        switch (entity.getClassEntityId()) {
            case 1:
                clock = (Distributions.exponencial(lambdaLight[index]));
                nextEntity = new LightAircraft(statistics);
                break;
            case 2:
                clock = (Distributions.exponencial(lambdaMid[index]));
                nextEntity = new MidAircraft(statistics);
                break;
            case 3:
                clock = (Distributions.normal(averageHeavy[index], desviationHeavy));
                nextEntity = new HeavyAircraft(statistics);
                break;
            default:
                clock = (Distributions.normal(averageMaintenance, desviationMaintenance));
                nextEntity = new Maintenance(statistics);
        }

        return new ArrivalEvent(actualEvent.getClock() + clock, nextEntity,
                ((ArrivalEvent) actualEvent).getSelectionPolicy());
    }
}
