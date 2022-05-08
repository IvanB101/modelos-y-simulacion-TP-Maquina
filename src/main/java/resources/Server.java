package resources;

import entities.Entity;

public abstract class Server {
    private static int idCount = 0;

    // attributes
    private int id;
    private boolean busy;
    private boolean maintenance;
    /**
     * init with 0 to avoid desynchronized in the firt arrival event.
     */
    private int idleTimeStartMark = 0;
    /**
     * init with 0 to avoid desynchronized in the firt arrival event.
     */
    private int idleTimeFinishMark = 0;
    private int idleTime;
    private int maxIdleTime = 0;

    // associations
    private Entity servedEntity;
    private Queue queue;

    public Server(Queue queue) {
        idCount++;
        this.id = idCount;
        this.busy = false;
        this.setMaintenance(false);
        this.idleTime = 0;
        this.servedEntity = null;
        this.queue = queue;
    }

    public boolean isMaintenance() {
        return maintenance;
    }

    public void setMaintenance(boolean maintenance) {
        this.maintenance = maintenance;
    }

    public abstract void addDurability(int durability);

    public abstract int getMaxDurability();

    public int getId() {
        return this.id;
    }

    public static int getIdCount() {
        return idCount;
    }

    public static void setIdCount(int aIdCount) {
        idCount = aIdCount;
    }

    public boolean isBusy() {
        return this.busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public void setIdleTimeStartMark(int idleTimeStartMark) {
        this.idleTimeStartMark = idleTimeStartMark;
    }

    public void setIdleTimeFinishMark(int idleTimeFinishMark) {
        this.idleTimeFinishMark = idleTimeFinishMark;

        try {
            if (this.idleTimeStartMark != -1 && this.idleTimeFinishMark != -1
                    && this.idleTimeStartMark <= this.idleTimeFinishMark) {

                int idleTime = this.idleTimeFinishMark - this.idleTimeStartMark;
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

    public int getIdleTime() {
        return idleTime;
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

    public int getMaxIdleTime() {
        return maxIdleTime;
    }
}