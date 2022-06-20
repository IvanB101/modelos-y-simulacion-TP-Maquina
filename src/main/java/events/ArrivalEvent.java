package events;

import java.util.List;
import resources.Server;
import utils.Statistics;
import entities.Entity;
import engine.FutureEventList;
import policies.ServerSelectionPolicy;
import behaviors.ArrivalEventBehavior;
import behaviors.EndOfServiceEventBehavior;

public class ArrivalEvent extends Event {
    private ServerSelectionPolicy selectionPolicy;
    private EndOfServiceEventBehavior endOfServiceEventBehavior;

    public ArrivalEvent(double clock, Entity entity, ServerSelectionPolicy policy) {
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
    public void planificate(List<Server> servers, FutureEventList fel, Statistics statistics) {
        Entity entity = this.getEntity();
        Server server = this.getSelectionPolicy().selectServer(servers, entity.getClassEntityId());
        entity.setEvent(this);
        entity.setAttendingServer(server);

        if (server.isBusy()) {
            server.getQueue().enqueue(entity);
        } else {
            server.setServedEntity(entity);
            server.setBusy(true);
            fel.insert(this.endOfServiceEventBehavior.nextEvent(this, entity, statistics));
            server.setIdleTimeFinishMark(this.getClock());
        }

        if (entity.getClassEntityId() == 4) {
            server.setMaintenance(true);
        }

        fel.insert(this.getEventBehavior().nextEvent(this, entity, statistics));
    }

    @Override
    public String toString() {
        return "Type: Arrival        -- Clock: " + this.getClock() + "s -- entity: " +
                this.getEntity().toString();
    }
}