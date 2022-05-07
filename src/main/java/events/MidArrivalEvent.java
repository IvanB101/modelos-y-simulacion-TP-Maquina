package events;

import entities.Entity;
import policies.ServerSelectionPolicy;

public class MidArrivalEvent extends ArrivalEvent{

    public MidArrivalEvent(int clock, Entity entity, ServerSelectionPolicy policy) {
        super(clock, entity, policy);
    }
    
}
