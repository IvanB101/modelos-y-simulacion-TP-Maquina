package resources;

public class HeavyAirstrip extends Server {
    private static final int maxDurability = 5000;
    private static final int classServerId = 3;
    private double durability;

    public HeavyAirstrip(Queue queue) {
        super(queue);
        this.durability = maxDurability;
    }

    public int getClassserverid() {
        return classServerId;
    }

    @Override
    public void addDurability(double durability) {
        this.durability = (this.durability + durability) % maxDurability;
    }
    @Override
    public String toString() {
        return "Airstrip " + this.getId() + " -- busy? : " + this.isBusy() + " -- attending: " + this.getServedEntity()
                + " >> heavy Airstrip";
    }

    public double getDurability() {
        return durability;
    }

    @Override
    public int getMaxDurability() {
        return maxDurability;
    }
}