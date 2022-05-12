package entities;

import utils.Distributions;
import utils.Statistics;

public class HeavyAircraft extends Entity {
    private static final int classEntityId = 3;
    private static final int[] valuesHeavy = { -3, -6 };

    private int typeId;

    public HeavyAircraft(Statistics statistics) {
        super(statistics);
        statistics.addIdCount(classEntityId);
        this.typeId = statistics.getIdCount(classEntityId);
    }

    @Override
    public String toString() {
        return "id = " + this.getId() + " Type id: " + this.getTypeId() + " >> heavy aircraft";
    }

    @Override
    public int getClassEntityId() {
        return classEntityId;
    }

    public static int getClassId() {
        return classEntityId;
    }

    @Override
    public void affectAirstrip() {
        this.getAttendingServer().addDurability( Distributions.uniform(valuesHeavy[0], valuesHeavy[1]));
    }

    public int getTypeId() {
        return typeId;
    }
}