package events;

import java.util.List;
import resources.Server;
import utils.Statistics;
import engine.FutureEventList;

public class StopExecutionEvent extends Event {

    public StopExecutionEvent(double clock) {
        super(clock, null, null);
        this.setPriority(1);
    }

    @Override
    public String toString() {
        return "Type: Stop Execution -- Clock: " + this.getClock() + "s -- entity: null";
    }

    @Override
    public void planificate(List<Server>servers, FutureEventList fel, Statistics statistics) {
    }
}