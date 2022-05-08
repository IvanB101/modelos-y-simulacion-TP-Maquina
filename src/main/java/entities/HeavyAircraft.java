package entities;

import resources.Server;
import utils.Distributions;
import utils.Statistics;

public class HeavyAircraft extends Entity {
    private static final int classEntityId = 3;
    private static final int[] valuesHeavy = { -3, -6 };

    private int typeId;

    public HeavyAircraft(Server server) {
        super(server);
        Statistics.addIdCount(classEntityId);
        this.typeId = Statistics.getIdCount(classEntityId);
    }

    @Override
    public String toString() {
        return "id = " + this.getId() + " Type id: " + this.getTypeId() + " >> heavy aircraft";
    }

    @Override
    public int getClassEntityId() {
        return classEntityId;
    }

    @Override
    public void affectAirstrip() {
        this.getAttendingServer().addDurability((int) Distributions.uniform(valuesHeavy[0], valuesHeavy[1]));
    }

    public int getTypeId() {
        return typeId;
    }
}