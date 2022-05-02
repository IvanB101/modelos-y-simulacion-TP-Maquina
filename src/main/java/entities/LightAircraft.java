package entities;

import resources.Server;

public class LightAircraft extends Entity {
    public LightAircraft(Server server) {
        super(server);
    }

    @Override
    public String toString() {
        return "id = " + this.getId() + " >> light aircraft";
    }
}
