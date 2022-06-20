package resources;

import utils.Statistics;

public class MidAirstrip extends Server {
    private static final int maxDurability = 3000;
    private static final int classServerId = 2;

    private int typeId;

    public MidAirstrip(Queue queue, Statistics statistics) {
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
