
package entities;

import resources.Server;
import utils.Statistics;

public class Maintenance extends Entity {
    private static final int classEntityId = 4;

    private int typeId;

    public Maintenance(Server server) {
        super(server);
        Statistics.addIdCount(classEntityId);
        this.typeId = Statistics.getIdCount(classEntityId);
    }

    @Override
    public String toString() {
        return "id = " + this.getId() + " Type id: " + this.getTypeId() + " >> maintenance";
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
        this.getAttendingServer().addDurability((this.getAttendingServer().getMaxDurability()*0.15));
    }
}
