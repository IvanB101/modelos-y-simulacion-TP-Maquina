package utils;

public class Statistics {
    //Entity statistics
    private static final int classesNumber = 5;

    //private static String[]encabezado = {"General", "Light Aircraft", "Mid Weight Aircraft", "Heavy Aircraft", "Maintenance"};
    private static int[]idCount = new int[classesNumber];
    private static double[]totalWaitingTime = new double[classesNumber];
    private static double[]maxWaitingTime = new double[classesNumber];
    private static double[]totalTransitTime = new double[classesNumber];
    private static double[]maxTransitTime = new double[classesNumber];

    public static int getIdCount(int classEntityId) {
        return idCount[classEntityId];
    }

    public static void addIdCount(int classEntityId) {
        Statistics.idCount[classEntityId]++;
    }

    public static double getTotalWaitingTime(int classEntityId) {
        return totalWaitingTime[classEntityId];
    }

    public static void accumulateWaitingTime(double WaitingTime, int classEntityId) {
        Statistics.totalWaitingTime[classEntityId] += WaitingTime;
    }

    public static double getMaxWaitingTime(int classEntityId) {
        return maxWaitingTime[classEntityId];
    }

    public static void setMaxWaitingTime(double maxWaitingTime, int classEntityId) {
        Statistics.maxWaitingTime[classEntityId] = maxWaitingTime;
    }

    public static double getTotalTransitTime(int classEntityId) {
        return totalTransitTime[classEntityId];
    }

    public static void accumulateTransitTime(double TransitTime, int classEntityId) {
        Statistics.totalTransitTime[classEntityId] += TransitTime;
    }

    public static double getMaxTransitTime(int classEntityId) {
        return maxTransitTime[classEntityId];
    }

    public static void setMaxTransitTime(double maxTransitTime, int classEntityId) {
        Statistics.maxTransitTime[classEntityId] = maxTransitTime;
    }
}
