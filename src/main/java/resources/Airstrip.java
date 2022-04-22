package resources;

public class Airstrip extends Server {

    public Airstrip(Queue queue) {
        super(queue);
    }

    @Override
    public String toString() {
        return "Airstrip " + this.getId() + " -- busy? : " + this.isBusy() + " -- attending: " + this.getServedEntity();
    }
}