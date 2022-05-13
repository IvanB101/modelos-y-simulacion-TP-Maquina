package resources;

import utils.Statistics;

public class LightAirstrip extends Server {
    private static final int maxDurability = 1000;
    private static final int classServerId = 1;

    private int typeId;

    public LightAirstrip(Queue queue, Statistics statistics) {
        super(queue, statistics);
        statistics.addServerIdCount(classServerId);
        this.typeId = statistics.getServerIdCount(classServerId);
    }

    public int getClassServerid() {
        return classServerId;
    }
    
    public int getTypeId() {
        return typeId;
    }

    @Override
    public String toString() {
        return "Airstrip " + this.getId() + " -- busy? : " + this.isBusy() + " -- attending: " + this.getServedEntity()
                + " >> light Airstrip";
    }

    @Override
    public int getMaxDurability() {
        return maxDurability;
    }
}
