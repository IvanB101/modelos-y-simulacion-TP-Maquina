package resources;

public class MidAirstrip extends Server {
    private static final int maxDurability = 3000;
    private static final int classServerId = 2;

    public MidAirstrip(Queue queue) {
        super(queue);
    }

    public int getClassServerid() {
        return classServerId;
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
