package events;

import java.util.List;
import entities.Entity;
import resources.Server;
import utils.Statistics;
import engine.FutureEventList;
import behaviors.EndOfServiceEventBehavior;

public class EndOfServiceEvent extends Event {
    public EndOfServiceEvent(double clock, Entity entity) {
        super(clock, entity, EndOfServiceEventBehavior.getInstance());
        this.setPriority(0);
    }

    @Override
    public String toString() {
        return "Type: End of Service -- Clock: " + this.getClock() + "s -- entity: " +
                this.getEntity().toString();
    }

    @Override
    public void planificate(List<Server> servers, FutureEventList fel, Statistics statistics) {
        Entity entity = this.getEntity();
        Server server = entity.getAttendingServer();
        entity.setEvent(this);

        // For not taking maintenance statistics into account
        if (entity.getClassEntityId() != 4) {
            // Adds the transit time of the entity to the statistics, which is calculated in
            // the EndOfServiceBehavior
            double transit = entity.getTransitTime();
            if (transit > statistics.getMaxTransitTime(entity.getClassEntityId())) {
                statistics.setMaxTransitTime(transit, entity.getClassEntityId());
            }
            statistics.accumulateTransitTime(transit, entity.getClassEntityId());

            // Adds the waiting time of the entity to the statistics, which is calculated in
            // the EndOfServiceBehavior
            double wait = entity.getWaitingTime();
            if (wait > statistics.getMaxWaitingTime(entity.getClassEntityId())) {
                statistics.setMaxWaitingTime(wait, entity.getClassEntityId());
            }
            statistics.accumulateWaitingTime(wait, entity.getClassEntityId());
        } else {
            server.setMaintenance(false);
        }

        if (!(server.getQueue().isEmpty())) {
            fel.insert(this.getEventBehavior().nextEvent(this, server.getQueue().next(), statistics));
        } else {
            server.setBusy(false);
            server.setIdleTimeStartMark(this.getClock());
        }
    }
}