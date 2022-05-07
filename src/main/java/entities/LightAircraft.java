package entities;

import resources.Server;
import utils.Distributions;

public class LightAircraft extends Entity {
    private static int idCount = 0;
    private static int totalWaitingTime = 0;
    private static int maxWaitingTime = 0;
    private static int totalTransitTime = 0;
    private static int maxTransitTime = 0;
    private final int[]valuesLight= {0,-1};

    private int typeId;

    public LightAircraft(Server server) {
        super(server);
        idCount++;
        this.typeId = idCount;
    }

    @Override
    public String toString() {
        return "id = " + this.getId() + " Type id: " + this.getTypeId() + " >> light aircraft";
    }

    public static int getIdCount() {
        return idCount;
    }

    public static int getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public static void accumulateWaitingTime(int WaitingTime) {
        LightAircraft.totalWaitingTime += WaitingTime;
    }

    public static int getMaxWaitingTime() {
        return maxWaitingTime;
    }

    public static void setMaxWaitingTime(int maxWaitingTime) {
        LightAircraft.maxWaitingTime = maxWaitingTime;
    }

    public static int getTotalTransitTime() {
        return totalTransitTime;
    }

    public static void accumulateTransitTime(int TransitTime) {
        LightAircraft.totalTransitTime += TransitTime;
    }

    public static int getMaxTransitTime() {
        return maxTransitTime;
    }

    public static void setMaxTransitTime(int maxTransitTime) {
        LightAircraft.maxTransitTime = maxTransitTime;
    }

    public int getTypeId() {
        return typeId;
    }
    @Override
    public void affectAirstrip(){
        this.getAttendingServer().addDurability((int)Distributions.uniform(valuesLight[0],valuesLight[1],super.getRandomizer()));
    }
}
