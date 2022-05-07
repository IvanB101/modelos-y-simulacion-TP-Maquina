package behaviors;

import events.Event;
import entities.Entity;
import entities.HeavyAircraft;
import entities.LightAircraft;
import entities.MidAircraft;
import utils.Randomizer;
import utils.CustomRandomizer;
import utils.Distributions;
import events.EndOfServiceEvent;

public class EndOfServiceEventBehavior extends EventBehavior {
    private static EndOfServiceEventBehavior endOfServiceEventBehavior;
    private final int[]valuesLight = {5, 10, 15};
    private final double[]accProbabilityLight = {0.363, 0.838, 1};
    private final int[]valuesHeavy = {40, 50};
    private final double[]accProbabilityHeavy = {0.65, 1};
    private final int[]valuesMid= {10,20};
    private final int[]valuesMaintenance= {12*60,24*60};

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

        if (entity instanceof HeavyAircraft){
            clock = Distributions.discreteEmpiric(valuesHeavy, accProbabilityHeavy, super.getRandomizer());
        } else if (entity instanceof MidAircraft) {
            clock =(int)Distributions.uniform(valuesMid[0],valuesMid[1], super.getRandomizer());
        } else if (entity instanceof LightAircraft) {
            clock = Distributions.discreteEmpiric(valuesLight, accProbabilityLight, super.getRandomizer());
        } else {
            clock =(int)Distributions.uniform(valuesMaintenance[0],valuesMaintenance[1], super.getRandomizer());
        }
        //TODO
        Event event = new EndOfServiceEvent(actualEvent.getClock() + clock, entity);
        entity.setTransitTime(clock);

        return event;
    }
}