package resources;

public class MidAirstrip extends Server {
    private static final int maxDurability = 3000;
    private static final int classServerId = 2;
    private double durability;

    public MidAirstrip(Queue queue) {
        super(queue);
        this.durability = maxDurability;
    }

    public int getClassserverid() {
        return classServerId;
    }

    public double getDurability(){
        return durability;
    }

    public void addDurability(double durability) {
        this.durability = (this.durability + durability) % maxDurability;
    }

    @Override
    public String toString() {
        return "Airstrip " + this.getId() + " -- busy? : " + this.isBusy() + " -- attending: " + this.getServedEntity()
                + " >> mid weight Airstrip";
    }

    @Override
    public int getMaxDurability() {
        return maxDurability;
    }
}
