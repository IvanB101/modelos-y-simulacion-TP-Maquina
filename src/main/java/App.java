import javax.management.InvalidAttributeValueException;

import engine.AirportSimulation;
import engine.Engine;
import policies.MultipleServerModelPolicy;
import policies.ServerSelectionPolicy;
import utils.Data.ReplicationData;

public class App {
    private static final int MIN_PER_DAY = 1440;
    private static final int NUMBER_OF_DAYS = 28;
    private static final int EXECUTION_TIME = MIN_PER_DAY * NUMBER_OF_DAYS;
    // Parameters to changes for tests
    private static final int[] configuration = { 1, 1, 1 };
    private static final int repetitions = 2;
    private static final ServerSelectionPolicy serverSelectionPolicy = MultipleServerModelPolicy.getInstance();

    public static void main(String[] args) {
        ReplicationData replicationData = new ReplicationData();

        try {
            for (int i = 0; i < repetitions; i++) {
                Engine engine = new AirportSimulation(configuration, EXECUTION_TIME, serverSelectionPolicy,
                0, 0.05);
                engine.execute();
                engine.generateReport();
                engine.showReport();

                replicationData.addReplication(((AirportSimulation)engine).getStatistics());
            }

            replicationData.showReport();
        } catch (InvalidAttributeValueException e) {
            System.out.println(e.getMessage());
        }
    }
}