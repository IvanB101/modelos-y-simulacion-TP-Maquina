package behaviors;

import events.Event;
import entities.Entity;
import entities.HeavyAircraft;
import entities.LightAircraft;
import entities.Maintenance;
import entities.MidAircraft;
import utils.Randomizer;
import utils.Statistics;
import utils.CustomRandomizer;
import utils.Distributions;
import events.EndOfServiceEvent;

public class EndOfServiceEventBehavior extends EventBehavior {
    private static EndOfServiceEventBehavior endOfServiceEventBehavior;
    private final int[] valuesLight = { 5, 10, 15 };
    private final double[] accProbabilityLight = { 0.363, 0.838, 1 };
    private final int[] valuesHeavy = { 40, 50 };
    private final double[] accProbabilityHeavy = { 0.65, 1 };
    private final int[] valuesMid = { 10, 20 };
    private final int[] valuesMaintenance = { 12 * 60, 24 * 60 };

    private EndOfServiceEventBehavior(Randomizer randomizer) {
        super();
    }

    public static EndOfServiceEventBehavior getInstance() {
        if (EndOfServiceEventBehavior.endOfServiceEventBehavior == null)
            EndOfServiceEventBehavior.endOfServiceEventBehavior = new EndOfServiceEventBehavior(
                    CustomRandomizer.getInstance());

        return endOfServiceEventBehavior;
    }

    @Override
    public Event nextEvent(Event actualEvent, Entity entity, Statistics statistics) {
        double clock = 0;

        // Calculates and gives the waiting time for the entity
        double waitingTime = actualEvent.getClock() - entity.getArrivalEvent().getClock();
        entity.setWaitingTime(waitingTime);

        if (entity instanceof HeavyAircraft) {
            clock = Distributions.discreteEmpiric(valuesHeavy, accProbabilityHeavy);
        } else if (entity instanceof MidAircraft) {
            clock = Distributions.uniform(valuesMid[0], valuesMid[1]);
        } else if (entity instanceof LightAircraft) {
            clock = Distributions.discreteEmpiric(valuesLight, accProbabilityLight);
        } else if (entity instanceof Maintenance) {
            if (actualEvent.getClock() == 0) {
                // Control for the duration of the first maintenance to be 0
                clock = 0;
            } else {
                clock = Distributions.uniform(valuesMaintenance[0], valuesMaintenance[1]);
            }
        } else {
            System.out.println("Invalid entity type");
        }

        // Calculates and gives the transit time for the entity
        double transitTime = waitingTime + clock;
        entity.setTransitTime(transitTime);

        entity.affectAirstrip();
        return new EndOfServiceEvent(actualEvent.getClock() + clock, entity);
    }
}