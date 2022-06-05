import javax.management.InvalidAttributeValueException;

import engine.AirportSimulation;
import engine.Engine;
import policies.MultipleServerModelPolicy;
import policies.ServerSelectionPolicy;

public class App {
    private static final int MIN_PER_DAY = 1440;
    private static final int NUMBER_OF_DAYS = 28;
    private static final int EXECUTION_TIME = MIN_PER_DAY * NUMBER_OF_DAYS;
    // Parameters to changes for tests
    private static final int[] configuration = { 1, 1, 1 };
    // private static final int repetitions = 1;
    private static final ServerSelectionPolicy serverSelectionPolicy = MultipleServerModelPolicy.getInstance();

    public static void main(String[] args) {
        try {
            // Last parameter is an optional seed, if it is 0 then it uses the PC clock
            Engine engine = new AirportSimulation(configuration, EXECUTION_TIME, serverSelectionPolicy,
                    0, 0.05);
            engine.execute();
            engine.generateReport();
            engine.showReport();
        } catch (InvalidAttributeValueException e) {
            System.out.println(e.getMessage());
        }
    }
}