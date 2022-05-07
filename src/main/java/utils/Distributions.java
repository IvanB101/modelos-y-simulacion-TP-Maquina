package utils;

public abstract class Distributions {
    private static final int numNormal = 12;

    public static int discreteEmpiric(int[] values, double[] accProbability, Randomizer randomizer) {
        double random = randomizer.nextRandom();

        for (int i = 0; i < accProbability.length; i++) {
            if (random <= accProbability[i]) {
                return values[i];
            }
        }

        return values[values.length - 1];
    }

    public static double uniform(int a, int b, Randomizer randomizer) {
        double random = randomizer.nextRandom();

        return a + (b - a) * random;
    }

    public static double exponencial(int lambda, Randomizer randomizer) {
        double random = randomizer.nextRandom();

        return (1 / (double) lambda) * (Math.log(1 - random));
    }

    public static double normal(int average, double desviation, Randomizer randomizer) {
        double n = 0.0;

        //Generation of normal variable
        for (int i = 0; i < numNormal; i++) {
            n += randomizer.nextRandom();
        }
        n = (n - numNormal / 2) / (numNormal / 12);

        return n * desviation + average;
    }
}
