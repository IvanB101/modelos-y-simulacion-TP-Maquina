package events;

import java.util.List;
import entities.Entity;
import resources.Server;
import utils.Statistics;
import engine.FutureEventList;
import behaviors.EndOfServiceEventBehavior;

public class EndOfServiceEvent extends Event {
    public EndOfServiceEvent(int clock, Entity entity) {
        super(clock, entity, EndOfServiceEventBehavior.getInstance());
        this.setPriority(0);
    }

    @Override
    public String toString() {
        return "Type: End of Service -- Clock: " + this.getClock() + "s -- entity: " +
                this.getEntity().toString();
    }

    @Override
    public void planificate(List<Server> servers, FutureEventList fel) {
        Entity entity = this.getEntity();
        Server server = entity.getAttendingServer();
        entity.setEvent(this);

        int transit = this.getClock() - entity.getArrivalEvent().getClock();

        if (transit > Statistics.getMaxTransitTime(entity.getClassEntityId())) {
            Statistics.setMaxTransitTime(transit, entity.getClassEntityId());
        }
        Statistics.accumulateTransitTime(transit, entity.getClassEntityId());

        if (!server.getQueue().isEmpty()) {
            int wait = this.getClock() - server.getQueue().checkNext().getArrivalEvent().getClock();
            if (wait > Statistics.getMaxWaitingTime(entity.getClassEntityId())) {
                Statistics.setMaxWaitingTime(wait, entity.getClassEntityId());
            }

            Statistics.accumulateWaitingTime(wait, entity.getClassEntityId());

            fel.insert(this.getEventBehavior().nextEvent(this, server.getQueue().next(), null));
        } else {
            server.setBusy(false);
            server.setIdleTimeStartMark(this.getClock());
        }
    }
}