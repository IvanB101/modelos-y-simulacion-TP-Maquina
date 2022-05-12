package resources;

public class LightAirstrip extends Server {
    private static final int maxDurability = 1000;
    private static final int classServerId = 1;

    public LightAirstrip(Queue queue) {
        super(queue);
    }

    public int getClassServerid() {
        return classServerId;
    }

    @Override
    public String toString() {
        return "Airstrip " + this.getId() + " -- busy? : " + this.isBusy() + " -- attending: " + this.getServedEntity()
                + " >> light Airstrip";
    }

    @Override
    public int getMaxDurability() {
        return maxDurability;
    }
}
