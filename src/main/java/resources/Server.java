package resources;

import entities.Entity;
import utils.Statistics;

public abstract class Server {
    private static final int classServerId = 0;

    // attributes
    private int id;
    private boolean busy;
    private boolean maintenance;
    private double durability;
    /**
     * init with 0 to avoid desynchronized in the firt arrival event.
     */
    private double idleTimeStartMark = 0;
    /**
     * init with 0 to avoid desynchronized in the firt arrival event.
     */
    private double idleTimeFinishMark = 0;
    private double idleTime;
    private double maxIdleTime = 0;

    // associations
    private Entity servedEntity;
    private Queue queue;

    public Server(Queue queue, Statistics statistics) {
        statistics.addServerIdCount(classServerId);
        this.id = statistics.getServerIdCount(classServerId);
        this.busy = false;
        this.setMaintenance(false);
        this.idleTime = 0;
        this.servedEntity = null;
        this.queue = queue;
        this.durability = getMaxDurability();
    }

    public int getClassServerid() {
        return classServerId;
    }

    public boolean isMaintenance() {
        return maintenance;
    }

    public void setMaintenance(boolean maintenance) {
        this.maintenance = maintenance;
    }

    public void addDurability(double durability) {
        this.durability += durability % getMaxDurability();
    }

    public abstract int getMaxDurability();

    public int getId() {
        return this.id;
    }

    public boolean isBusy() {
        return this.busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public void setIdleTimeStartMark(double idleTimeStartMark) {
        this.idleTimeStartMark = idleTimeStartMark;
    }

    public void setIdleTimeFinishMark(double idleTimeFinishMark) {
        this.idleTimeFinishMark = idleTimeFinishMark;

        try {
            if (this.idleTimeStartMark != -1 && this.idleTimeFinishMark != -1
                    && this.idleTimeStartMark <= this.idleTimeFinishMark) {

                double idleTime = this.idleTimeFinishMark - this.idleTimeStartMark;
                this.idleTime += idleTime;

                if (idleTime > this.maxIdleTime) {
                    this.maxIdleTime = idleTime;
                }

                this.idleTimeStartMark = -1;
                this.idleTimeFinishMark = -1;
            } else {
                throw new Exception("desynchronized idle time marks");
            }
        } catch (Exception exception) {

            exception.printStackTrace();
        }
    }

    public double getIdleTime() {
        return idleTime;
    }

    public double getDurability() {
        return durability;
    }

    public Entity getServedEntity() {
        return servedEntity;
    }

    public void setServedEntity(Entity servedEntity) {
        this.servedEntity = servedEntity;
    }

    public Queue getQueue() {
        return this.queue;
    }

    public double getMaxIdleTime() {
        return maxIdleTime;
    }
}