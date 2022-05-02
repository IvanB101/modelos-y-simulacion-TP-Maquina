/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources;

public class LightAirstrip extends Server {
    private static final int maxDurability = 1000;
    private int durability;

    public LightAirstrip(Queue queue) {
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
                + " >> light Airstrip";
    }
}
