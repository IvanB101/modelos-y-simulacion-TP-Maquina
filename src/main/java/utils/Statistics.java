package utils;

public class Statistics {
    //Entity statistics
    private static final int classesNumber = 5;

    //private static String[]encabezado = {"General", "Light Aircraft", "Mid Weight Aircraft", "Heavy Aircraft", "Maintenance"};
    private static int[]idCount = new int[classesNumber];
    private static int[]totalWaitingTime = new int[classesNumber];
    private static int[]maxWaitingTime = new int[classesNumber];
    private static int[]totalTransitTime = new int[classesNumber];
    private static int[]maxTransitTime = new int[classesNumber];

    public static int getIdCount(int classEntityId) {
        return idCount[classEntityId];
    }

    public static void addIdCount(int classEntityId) {
        Statistics.idCount[classEntityId]++;
    }

    public static int getTotalWaitingTime(int classEntityId) {
        return totalWaitingTime[classEntityId];
    }

    public static void accumulateWaitingTime(int WaitingTime, int classEntityId) {
        Statistics.totalWaitingTime[classEntityId] += WaitingTime;
    }

    public static int getMaxWaitingTime(int classEntityId) {
        return maxWaitingTime[classEntityId];
    }

    public static void setMaxWaitingTime(int maxWaitingTime, int classEntityId) {
        Statistics.maxWaitingTime[classEntityId] = maxWaitingTime;
    }

    public static int getTotalTransitTime(int classEntityId) {
        return totalTransitTime[classEntityId];
    }

    public static void accumulateTransitTime(int TransitTime, int classEntityId) {
        Statistics.totalTransitTime[classEntityId] += TransitTime;
    }

    public static int getMaxTransitTime(int classEntityId) {
        return maxTransitTime[classEntityId];
    }

    public static void setMaxTransitTime(int maxTransitTime, int classEntityId) {
        Statistics.maxTransitTime[classEntityId] = maxTransitTime;
    }
}
