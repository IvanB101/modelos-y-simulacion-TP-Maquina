package resources;

public class HeavyAirstrip extends Server {
    private static final int maxDurability = 5000;
    private int durability;

    public HeavyAirstrip(Queue queue) {
        super(queue);
        this.durability = maxDurability;
    }

    @Override
    public void addDurability(int durability) {
        this.durability = (this.durability + durability) % maxDurability;
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