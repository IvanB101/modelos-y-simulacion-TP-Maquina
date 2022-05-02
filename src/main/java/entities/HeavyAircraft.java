package entities;

import resources.Server;

public class HeavyAircraft extends Entity {
    public HeavyAircraft(Server server) {
        super(server);
    }

    @Override
    public String toString() {
        return "id = " + this.getId() + " >> heavy aircraft";
    }
}