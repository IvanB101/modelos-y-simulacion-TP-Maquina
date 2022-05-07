
package entities;

import resources.Server;

public class Maintenance extends Entity {
    private static int idCount = 0;

    private int typeId;

    public Maintenance(Server server) {
        super(server);
        idCount++;
        this.typeId = idCount;
    }

    @Override
    public String toString() {
        return "id = " + this.getId() + " Type id: " + this.getTypeId() + " >> maintenance";
    }

    public int getTypeId() {
        return typeId;
    }

    @Override
    public void affectAirstrip(){
        this.getAttendingServer().addDurability((int)(this.getAttendingServer().getMaxDurability()*0.15));
    }
}
