package entities;

import resources.Server;

public class MidAircraft extends Entity {
    public MidAircraft(Server server) {
        super(server);
    }

    @Override
    public String toString() {
        return "id = " + this.getId() + " >> mid weight aircraft";
    }
}
