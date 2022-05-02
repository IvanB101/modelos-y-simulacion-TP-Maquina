package events;

import java.util.List;
import resources.Server;
import entities.Entity;
import engine.FutureEventList;
import policies.ServerSelectionPolicy;
import behaviors.ArrivalEventBehavior;
import behaviors.EndOfServiceEventBehavior;

public class ArrivalEvent extends Event {
    private ServerSelectionPolicy selectionPolicy;
    private EndOfServiceEventBehavior endOfServiceEventBehavior;

    public ArrivalEvent(int clock, Entity entity, ServerSelectionPolicy policy) {
        super(clock, entity, ArrivalEventBehavior.getInstance());
        this.selectionPolicy = policy;
        this.setPriority(2);
        this.endOfServiceEventBehavior = EndOfServiceEventBehavior.getInstance();
    }

    public ServerSelectionPolicy getSelectionPolicy() {
        return this.selectionPolicy;
    }

    public EndOfServiceEventBehavior getEndOfServiceEventBehavior() {
        return this.endOfServiceEventBehavior;
    }

    @Override
    public void planificate(List<Server> servers, FutureEventList fel) {
        Server server = this.getSelectionPolicy().selectServer(servers);
        this.getEntity().setEvent(this);

        if (server.isBusy()) {
            server.getQueue().enqueue(this.getEntity());
        } else {
            server.setServedEntity(this.getEntity());
            server.setBusy(true);
            fel.insert(this.endOfServiceEventBehavior.nextEvent(this, this.getEntity()));
            server.setIdleTimeFinishMark(this.getClock());
        }
        fel.insert(this.getEventBehavior().nextEvent(this, this.getEntity()));
    }

    @Override
    public String toString() {
        return "Type: Arrival        -- Clock: " + this.getClock() + "s -- entity: " +
                this.getEntity().toString();
    }
}