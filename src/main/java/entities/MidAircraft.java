package entities;

import resources.Server;
import utils.Distributions;
import utils.Statistics;

public class MidAircraft extends Entity {
    private static final int classEntityId = 2;
    private static final int[]valuesMid= {-1,-4};

    private int typeId;

    public MidAircraft(Server server) {
        super(server);
        Statistics.addIdCount(classEntityId);
        this.typeId = Statistics.getIdCount(classEntityId);
    }

    @Override
    public String toString() {
        return "id = " + this.getId() + " Type id: " + this.getTypeId() + " >> mid weight aircraft";
    }

    @Override
    public int getClassEntityId() {
        return classEntityId;
    }

    public int getTypeId() {
        return typeId;
    }

    @Override
    public void affectAirstrip(){
        this.getAttendingServer().addDurability((int)Distributions.uniform(valuesMid[0],valuesMid[1]));
    }
}
