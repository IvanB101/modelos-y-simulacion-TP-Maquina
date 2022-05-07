package events;

import entities.Entity;
import policies.ServerSelectionPolicy;

public class LightArrivalEvent extends ArrivalEvent{

    public LightArrivalEvent(int clock, Entity entity, ServerSelectionPolicy policy) {
        super(clock, entity, policy);
    }
    
}
