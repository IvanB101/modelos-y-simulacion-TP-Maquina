package entities;

import utils.Distributions;
import utils.Statistics;

public class LightAircraft extends Entity {
    private static final int classEntityId = 1;
    private static final int[]valuesLight= {0,-1};

    private int typeId;

    public LightAircraft(Statistics statistics) {
        super(statistics);
        statistics.addIdCount(classEntityId);
        this.typeId = statistics.getIdCount(classEntityId);
    }

    @Override
    public String toString() {
        return "id = " + this.getId() + " Type id: " + this.getTypeId() + " >> light aircraft";
    }

    @Override
    public int getClassEntityId() {
        return classEntityId;
    }

    public static int getClassId() {
        return classEntityId;
    }

    public int getTypeId() {
        return typeId;
    }
    @Override
    public void affectAirstrip(){
        this.getAttendingServer().addDurability(Distributions.uniform(valuesLight[0],valuesLight[1]));
    }
}
