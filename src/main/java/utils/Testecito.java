package utils;
import engine.AirportSimulation;
import engine.Engine;
import policies.MultipleServerModelPolicy;

public class Testecito {

    public Testecito() {
        int i;
        Statistics array = new Statistics(null);
        for (i=1; i < 2; i++){
            int amount[] = {6,2,2,2};
            array.setServerAmounts(amount);
            Engine engine = new AirportSimulation(amount, 40320, MultipleServerModelPolicy.getInstance(), 0);
            engine.execute();
            System.out.println("\n\nAirport nro " + i + "\n\n");
            engine.generateReport();
            engine.showReport();
    }
    }
    
}
