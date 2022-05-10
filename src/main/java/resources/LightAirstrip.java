package resources;

public class LightAirstrip extends Server {
    private static final int maxDurability = 1000;
    private static final int classServerId = 1;
    private double durability;

    public LightAirstrip(Queue queue) {
        super(queue);
        this.durability = maxDurability;
    }

    public int getClassserverid() {
        return classServerId;
    }

    public double getDurability() {
        return durability;
    }

    public void addDurability(double durability) {
        this.durability = (this.durability + durability) % maxDurability;
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
