package events;

import entities.Entity;
import policies.ServerSelectionPolicy;

public class HeavyArrivalEvent extends ArrivalEvent{

    public HeavyArrivalEvent(int clock, Entity entity, ServerSelectionPolicy policy) {
        super(clock, entity, policy);
    }

    
    
}
