package events;

import java.util.List;
import resources.Server;
import engine.FutureEventList;
import engine.AirportSimulation;

public class StopExecutionEvent extends Event {
    private AirportSimulation airportSimulation;

    public StopExecutionEvent(int clock, AirportSimulation airportSimulation) {
        super(clock, null, null);
        this.airportSimulation = airportSimulation;
        Event.END_TIME_DIGITS = ("" + this.airportSimulation.getEndTime()).length();
        this.setPriority(1);
    }

    @Override
    public String toString() {
        return "Type: Stop Execution -- Clock: " + this.getClock() + "s -- entity: null";
    }

    @Override
    public void planificate(List<Server> servers, FutureEventList fel) {
    }
}