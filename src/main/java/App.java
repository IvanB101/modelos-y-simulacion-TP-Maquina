import engine.AirportSimulation;
import engine.Engine;
import policies.OneServerModelPolicy;

public class App {
    private static final int MIN_PER_DAY = 1440;
    private static final int NUMBER_OF_DAYS = 28;
    private static final int EXECUTION_TIME = MIN_PER_DAY * NUMBER_OF_DAYS;

    //private static int SERVERS_NUMBER;

    public static void main(String[] args) {
        Engine engine = new AirportSimulation(1, EXECUTION_TIME, OneServerModelPolicy.getInstance());
        engine.execute();
        engine.generateReport();
        engine.showReport();
        engine.saveReport();
    }
}