package events;

import java.util.List;
import entities.Entity;
import resources.Server;
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
        Server server = this.getEntity().getAttendingServer();

        if (!server.getQueue().isEmpty()) {
            fel.insert(this.getEventBehavior().nextEvent(this, server.getQueue().next()));
        } else {
            server.setBusy(false);
        }
    }
}