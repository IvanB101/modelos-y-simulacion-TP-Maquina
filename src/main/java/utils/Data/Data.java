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
        if(data == null) {
            calculateData();
        }
        return data;
    }

    public abstract void calculateData();

    public void setData(double[][]data) {
        this.data = data;
    }

    public void updateData(double[][] data) {
        for (int i = 0; i < data.length; i++) {
            System.arraycopy(data[i], 0, this.data[i], 0, this.data[0].length);
        }
    }

    public abstract String toString();

    public double[][] mergeData(Data[]set, int fromC, int toC, int fromR, int toR) {
        double[][]ret = new double[set[0].getData().length][set[0].getData()[0].length];

        for (int i = 0; i < ret.length; i++) {
            
        }

        return ret;
    }

    public Statistics getStatistics() {
        return statistics;
    }
}
