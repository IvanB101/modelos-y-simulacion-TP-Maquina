package entities;

import resources.Server;
import utils.Distributions;

public class HeavyAircraft extends Entity {
    private static int idCount = 0;
    private static int totalWaitingTime = 0;
    private static int maxWaitingTime = 0;
    private static int totalTransitTime = 0;
    private static int maxTransitTime = 0;
    private final int[]valuesHeavy= {-3,-6};

    private int typeId;

    public HeavyAircraft(Server server) {
        super(server);
        idCount++;
        this.typeId = idCount;
    }

    @Override
    public String toString() {
        return "id = " + this.getId() + " Type id: " + this.getTypeId() + " >> heavy aircraft";
    }

    public static int getIdCount() {
        return idCount;
    }

    public static int getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public static void accumulateWaitingTime(int WaitingTime) {
        HeavyAircraft.totalWaitingTime += WaitingTime;
    }

    public static int getMaxWaitingTime() {
        return maxWaitingTime;
    }

    public static void setMaxWaitingTime(int maxWaitingTime) {
        HeavyAircraft.maxWaitingTime = maxWaitingTime;
    }

    public static int getTotalTransitTime() {
        return totalTransitTime;
    }

    public static void accumulateTransitTime(int TransitTime) {
        HeavyAircraft.totalTransitTime += TransitTime;
    }

    public static int getMaxTransitTime() {
        return maxTransitTime;
    }

    public static void setMaxTransitTime(int maxTransitTime) {
        HeavyAircraft.maxTransitTime = maxTransitTime;
    }

    public int getTypeId() {
        return typeId;
    }

    @Override
    public void affectAirstrip(){
        this.getAttendingServer().addDurability((int)Distributions.uniform(valuesHeavy[0],valuesHeavy[1],super.getRandomizer()));
    }
}