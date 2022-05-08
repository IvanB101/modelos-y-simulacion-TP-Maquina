package entities;

import resources.Server;
import utils.Distributions;
import utils.Statistics;

public class LightAircraft extends Entity {
    private static final int classEntityId = 1;
    private static final int[]valuesLight= {0,-1};

    private int typeId;

    public LightAircraft(Server server) {
        super(server);
        Statistics.addIdCount(classEntityId);
        this.typeId = Statistics.getIdCount(classEntityId);
    }

    @Override
    public String toString() {
        return "id = " + this.getId() + " Type id: " + this.getTypeId() + " >> light aircraft";
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
        this.getAttendingServer().addDurability((int)Distributions.uniform(valuesLight[0],valuesLight[1]));
    }
}
