
package utils;

import java.util.Random;

public class CustomRandomizer implements Randomizer {
    private static CustomRandomizer customRandomizer;

    private static long seed = 0;

    private Random randomizer;

    private CustomRandomizer() {
        long seed = System.currentTimeMillis();

        this.randomizer = new Random(seed);

        setSeed(seed);
    }

    private CustomRandomizer(long seed) {
        this.randomizer = new Random(seed);
    }

    public static CustomRandomizer getInstance() {
        if (CustomRandomizer.customRandomizer == null) {
            if(seed == 0) {
                CustomRandomizer.customRandomizer = new CustomRandomizer();
            } else {
                CustomRandomizer.customRandomizer = new CustomRandomizer(seed);
            }
        }
        return CustomRandomizer.customRandomizer;
    }

    @Override
    public double nextRandom() {
        return this.randomizer.nextDouble();
    }

    public static long getSeed() {
        return seed;
    }

    public static void setSeed(long seed) {
        CustomRandomizer.seed = seed;
    }
}