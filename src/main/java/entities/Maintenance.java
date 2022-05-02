
package entities;

import resources.Server;

public class Maintenance extends Entity {
    public Maintenance(Server server) {
        super(server);
    }

    @Override
    public String toString() {
        return "id = " + this.getId() + " >> maintenance";
    }
}
