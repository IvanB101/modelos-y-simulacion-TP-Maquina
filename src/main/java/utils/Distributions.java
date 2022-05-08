package utils;

public abstract class Distributions {
    private static final int numNormal = 12;
    private static Randomizer randomizer = CustomRandomizer.getInstance();

    public static int discreteEmpiric(int[] values, double[] accProbability) {
        double random = randomizer.nextRandom();

        for (int i = 0; i < accProbability.length; i++) {
            if (random <= accProbability[i]) {
                return values[i];
            }
        }

        return values[values.length - 1];
    }

    public static double uniform(int a, int b) {
        double random = randomizer.nextRandom();

        return a + (b - a) * random;
    }

    public static double exponencial(int lambda) {
        double random = randomizer.nextRandom();

        return (1 / (double) lambda) * (Math.log(1 - random));
    }

    public static double normal(double average, double desviation) {
        double n = 0.0;

        //Generation of normal variable
        for (int i = 0; i < numNormal; i++) {
            n += randomizer.nextRandom();
        }
        n = (n - numNormal / 2) / (numNormal / 12);

        return n * desviation + average;
    }
}
