import engine.AirportSimulation;
import engine.Engine;
import policies.MultipleServerModelPolicy;
import utils.Testecito;

public class App {
    private static final int MIN_PER_DAY = 1440;
    private static final int NUMBER_OF_DAYS = 28;
    private static final int EXECUTION_TIME = MIN_PER_DAY * NUMBER_OF_DAYS;
    public static void main(String[] args) {
        int[]configuration = {3,4,2};
        //Last parameter is an optional seed, if it is 0 then it uses the PC clock
        Engine engine = new AirportSimulation(configuration, EXECUTION_TIME, MultipleServerModelPolicy.getInstance(), 0);
        engine.execute();
        engine.generateReport();
        engine.showReport();
        //engine.saveReport();*/

        /*
        int[][]configurations = new int[1][3];
        int[]repetitions = new int[1];

        Testecito test = new Testecito(configurations, repetitions, EXECUTION_TIME);
        */
    }
}