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
     * @param set   array of set to make interval estimators from0
     * @return a string matrix with the with the interval estimator of the
     *         corresponding position in the set data matrices
     */
    public String[][] mergeData(Data[] set) {
        String[][] ret = new String[set[0].getData().length][set[0].getData()[0].length];

        for (int i = 0; i < set[0].getData().length; i++) {
            for (int j = 0; j < set[0].getData()[0].length; j++) {
                double[] temp = new double[set.length];

                for (int k = 0; k < set.length; k++) {
                    temp[k] = set[i].getData()[i][j];
                }

                ret[i][j] = getConfidenceInterval(temp);
            }
        }

        return ret;
    }

    /**
     * @param data an array of doubles
     * @return a string with the confidence interval taken from the values of the
     *         array estimating poblational parameters with the average an variance
     *         from data
     */
    public String getConfidenceInterval(double[] data) {
        String ret;
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

        halfWidth = statistics.getZAlfa() * deviation / Math.sqrt(n);

        ret = "(" + (average - halfWidth) + " - " + (average - halfWidth) + ")";

        return ret;
    }

    public Statistics getStatistics() {
        return statistics;
    }
}
