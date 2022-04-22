package entities;

import resources.Server;

public class Aircraft extends Entity {

    public Aircraft(Server server) {
        super(server);
    }

    @Override
    public String toString() {
        return "id = " + this.getId() + " >> defaul aircraft type";
    }
}