package behaviors;

import entities.Entity;
import entities.HeavyAircraft;
import entities.LightAircraft;
import entities.Maintenance;
import entities.MidAircraft;
import events.ArrivalEvent;
import events.Event;
import resources.Server;
import utils.CustomRandomizer;
import utils.Distributions;
import utils.Randomizer;

public class ArrivalEventBehavior extends EventBehavior {
    private static ArrivalEventBehavior arrivalEventBehavior;

    private final int averageHeavy[] = { 60, 30 };
    private final int desviationHeavy = 2;
    private final int lambdaLight[] = { 40, 20 };
    private final int lambdaMid[] = {30,15};
    private final double averageMaintenance = 5*24*60;
    private final double desviationMaintenance = 0.5*24*60;

    private ArrivalEventBehavior(Randomizer randomizer) {
        super();
    }

    public static ArrivalEventBehavior getInstance() {
        if (ArrivalEventBehavior.arrivalEventBehavior == null)
            ArrivalEventBehavior.arrivalEventBehavior = new ArrivalEventBehavior(CustomRandomizer.getInstance());

        return arrivalEventBehavior;
    }

    @Override
    public Event nextEvent(Event actualEvent, Entity entity, Server server) {
        int clock;
        Entity nextEntity;

        //Changes the parameter in rush hour for the distributions
        int index = 0;

        if ((actualEvent.getClock() % 720) >= 7*60 && (actualEvent.getClock() % 720) <= 10*60) {
            index = 1;
        }

        switch(entity.getClassEntityId()) {
            case 1:
                clock = (int) (Distributions.exponencial(lambdaMid[index]));
                nextEntity = new LightAircraft(server);
                break;
            case 2:
                clock = (int) (Distributions.exponencial(lambdaLight[index]));
                nextEntity = new MidAircraft(server);
                break;
            case 3:
                clock = (int) (Distributions.normal(averageHeavy[index], desviationHeavy));
                nextEntity = new HeavyAircraft(server);
                break;
            default:
                clock = (int) (Distributions.normal(averageMaintenance, desviationMaintenance));
                nextEntity = new Maintenance(server);
        }

        return new ArrivalEvent(clock, nextEntity, ((ArrivalEvent)actualEvent).getSelectionPolicy());
    }
}
