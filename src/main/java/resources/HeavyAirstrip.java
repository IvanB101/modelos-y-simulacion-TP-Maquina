package resources;

import utils.Statistics;

public class HeavyAirstrip extends Server {
    private static final int maxDurability = 5000;
    private static final int classServerId = 3;

    private int typeId;

    public HeavyAirstrip(Queue queue, Statistics statistics) {
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
    public int getMaxDurability() {
        return maxDurability;
    }
}