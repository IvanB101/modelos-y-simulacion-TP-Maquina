package utils.Data;

import utils.Statistics;

public abstract class Data {
    private double[][] data;
    private Statistics statistics;

    public Data(Statistics statistics) {
        this.statistics = statistics;
        this.data = null;
    }

    public double[][] getData() {
        if (data == null) {
            calculateData();
        }
        return data;
    }

    public abstract void calculateData();

    public void setData(double[][] data) {
        this.data = data;
    }

    public void updateData(double[][] data) {
        for (int i = 0; i < data.length; i++) {
            System.arraycopy(data[i], 0, this.data[i], 0, this.data[0].length);
        }
    }

    public abstract String toString();

    /**
     * @param set array of set to make interval estimators from, they must be of the
     *            same type
     * @return a double matrix with the last 2 indices corresponding to the indices
     *         in the data matrix, for the first index 0 is average 1 is half the
     *         width of the confidence interval
     */
    public static double[][][] mergeData(Data[] set, double zAlfa) {
        double[][][] ret = new double[2][set[0].getData().length][set[0].getData()[0].length];

        for (int i = 0; i < set[0].getData().length; i++) {
            for (int j = 0; j < set[0].getData()[0].length; j++) {
                double[] temp = new double[set.length];

                for (int k = 0; k < set.length; k++) {
                    temp[k] = set[k].getData()[i][j];
                }

                double[] interval = getConfidenceInterval(temp, zAlfa);

                ret[0][i][j] = interval[0];
                ret[1][i][j] = interval[1];
            }
        }

        return ret;
    }

    /**
     * @param data an array of doubles
     * @return an array with the first position corresponding to the average of the
     *         values and the second one with half the width of the confidence
     *         interval
     */
    public static double[] getConfidenceInterval(double[] data, double zAlfa) {
        int n = data.length;
        double average = 0, deviation = 0, halfWidth;

        for (int i = 0; i < n; i++) {
            average += data[i];
        }
        average /= n;

        for (int i = 0; i < n; i++) {
            deviation += Math.pow(data[i] - average, 2);
        }

        deviation /= (n - 1);
        deviation = Math.sqrt(deviation);

        halfWidth = zAlfa * deviation / Math.sqrt(n);

        double[] ret = { average, halfWidth };

        return ret;
    }

    public Statistics getStatistics() {
        return statistics;
    }
}
