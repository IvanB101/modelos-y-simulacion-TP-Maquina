package resources;

public class HeavyAirstrip extends Server {
    private static final int maxDurability = 5000;
    private static final int classServerId = 3;

    public HeavyAirstrip(Queue queue) {
        super(queue);
    }

    public int getClassServerid() {
        return classServerId;
    }

    @Override
    public String toString() {
        return "Airstrip " + this.getId() + " -- busy? : " + this.isBusy() + " -- attending: " + this.getServedEntity()
                + " >> heavy Airstrip";
    }

    @Override
    public int getMaxDurability() {
        return maxDurability;
    }
}