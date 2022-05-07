/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources;

/**
 *
 * @author Usuario
 */
public class MidAirstrip extends Server {
    private static final int maxDurability = 3000;
    private int durability;

    public MidAirstrip(Queue queue) {
        super(queue);
        this.durability = maxDurability;
    }

    public int getDurability() {
        return durability;
    }

    public void addDurability(int durability) {
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
