package resources;

import java.util.Iterator;
import java.util.LinkedList;
import entities.Entity;

public class CustomQueue extends Queue {
    public CustomQueue() {
        super(new LinkedList<Entity>());
    }

    public void enqueue(Entity entity) {
        this.getQueue().add(entity);

        if (this.getQueue().size() > this.getMaxSize()) {
            this.setMaxSize(this.getQueue().size());
        }
    }

    public Entity next() {
        return this.getQueue().remove();
    }

    public Entity checkNext() {
        return this.getQueue().peek();
    }

    @Override
    public String toString() {
        String ret = "server queue " + this.getAssignedServer().getId() + ":\n\t";

        Iterator<Entity> it = this.getQueue().iterator();

        while (it.hasNext()) {
            ret += it.next().toString();
        }

        return ret;
    }
}