import engine.AirportSimulation;
import engine.Engine;
import policies.MultipleServerModelPolicy;

public class App {
    private static final int MIN_PER_DAY = 1440;
    private static final int NUMBER_OF_DAYS = 28;
    private static final int EXECUTION_TIME = MIN_PER_DAY * NUMBER_OF_DAYS;

    public static void main(String[] args) {
        // Configuration of server (amounts of {Light Servers, Mid Servers, Heavy
        // Servers}) for a single run
        int[] configuration = { 1, 1, 1 };

        // Last parameter is an optional seed, if it is 0 then it uses the PC clock
        Engine engine = new AirportSimulation(configuration, EXECUTION_TIME, MultipleServerModelPolicy.getInstance(),
                0);
        engine.execute();
        engine.generateReport();
        engine.showReport();
        // engine.saveReport();
    }
}