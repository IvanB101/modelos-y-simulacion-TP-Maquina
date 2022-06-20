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
    private static final int lightAistripAmount = 3;
    private static final int midWeightAistripAmount = 0;
    private static final int heavyAistripAmount = 0;
    private static final int[] configuration = { lightAistripAmount, midWeightAistripAmount, heavyAistripAmount };
    private static final int repetitions = 50;
    private static final ServerSelectionPolicy serverSelectionPolicy = MultipleServerModelPolicy.getInstance();
    private static final double alfa = 0.05;

    public static void main(String[] args) {
        try {
            ReplicationData replicationData = new ReplicationData(alfa);

            for (int i = 0; i < repetitions; i++) {
                Engine engine = new AirportSimulation(configuration, EXECUTION_TIME, serverSelectionPolicy,
                0);
                engine.execute();
                // engine.generateReport();
                // engine.showReport();

                replicationData.addReplication(((AirportSimulation)engine).getStatistics());
            }

            replicationData.generateReport();
            replicationData.showReport();
        } catch (InvalidAttributeValueException e) {
            System.out.println(e.getMessage());
        }
    }
}