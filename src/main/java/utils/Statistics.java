package utils;

import java.util.List;

import entities.Entity;
import resources.Server;

public class Statistics {
    private List<Server> servers;

    //Atributes for server statistics
    private final int entityClassesNumber = 5;
    private final String[] entityHeader = { "General", "Light Aircraft", "Mid Weight Aircraft", "Heavy Aircraft",
            "Maintenance" };

    private int[] idCount = new int[entityClassesNumber];
    private double[] totalWaitingTime = new double[entityClassesNumber];
    private double[] maxWaitingTime = new double[entityClassesNumber];
    private double[] totalTransitTime = new double[entityClassesNumber];
    private double[] maxTransitTime = new double[entityClassesNumber];

    //Atributes for server statistics
    private final String[] serverHeader = { "General", "Light Server", "Mid Weight Server", "Heavy Server" };
    private final int serverClassesNumber = 4;
    private int[] serverAmounts;// = { 9, 3, 4, 2 };
    private int[] serverIdCount = new int[serverClassesNumber];

    // Entity statistics
    public Statistics(List<Server> servers, int[]configuration) {
        this.servers = servers;
        this.serverAmounts = new int[serverClassesNumber];
        this.serverAmounts[0] = 0;
        for (int i = 1; i < serverClassesNumber; i++) {
            this.serverAmounts[i] = configuration[i-1];
            this.serverAmounts[0] += configuration[i-1];
        }
    }

    public int getEntityClassesNumber() {
        return this.entityClassesNumber;
    }

    public String getClassEntityName(int classEntityId) {
        return entityHeader[classEntityId];
    }

    public int getEntityIdCount(int classEntityId) {
        return idCount[classEntityId];
    }

    public void addEntityIdCount(int classEntityId) {
        this.idCount[classEntityId]++;
    }

    public double getTotalWaitingTime(int classEntityId) {
        return totalWaitingTime[classEntityId];
    }

    public void accumulateWaitingTime(double WaitingTime, int classEntityId) {
        this.totalWaitingTime[classEntityId] += WaitingTime;
        this.totalWaitingTime[Entity.getClassId()] += WaitingTime;
    }

    public double getMaxWaitingTime(int classEntityId) {
        double ret = 0;

        if (classEntityId == 0) {
            for (int i = 1; i < entityClassesNumber; i++) {
                if (ret < getMaxWaitingTime(i)) {
                    ret = getMaxWaitingTime(i);
                }
            }
        } else {
            return maxWaitingTime[classEntityId];
        }

        return ret;
    }

    public void setMaxWaitingTime(double maxWaitingTime, int classEntityId) {
        this.maxWaitingTime[classEntityId] = maxWaitingTime;
    }

    public double getTotalTransitTime(int classEntityId) {
        return totalTransitTime[classEntityId];
    }

    public void accumulateTransitTime(double TransitTime, int classEntityId) {
        this.totalTransitTime[classEntityId] += TransitTime;
        this.totalTransitTime[Entity.getClassId()] += TransitTime;
    }

    public double getMaxTransitTime(int classEntityId) {
        double ret = 0;

        if (classEntityId == 0) {
            for (int i = 1; i < entityClassesNumber; i++) {
                if (ret < getMaxTransitTime(i)) {
                    ret = getMaxTransitTime(i);
                }
            }
        } else {
            return maxTransitTime[classEntityId];
        }

        return ret;
    }

    public void setMaxTransitTime(double maxTransitTime, int classEntityId) {
        this.maxTransitTime[classEntityId] = maxTransitTime;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    // Server statistics discriminated by class
    public int getServerIdCount(int classServerId) {
        return serverIdCount[classServerId];
    }

    public void addServerIdCount(int classServerId) {
        this.serverIdCount[classServerId]++;
    }

    public int getServerAmount(int classServerId) {
        return serverAmounts[classServerId];
    }

    public String getClassServerName(int classServerId) {
        return serverHeader[classServerId];
    }

    public int getServerClassesNumber() {
        return serverClassesNumber;
    }

    /**
     * @return total idle time of all servers with the specified id
     */
    public double getTotalIdleTime(int classServerId) {
        double totalIdleTime = 0.0;

        if (classServerId == 0) {
            for (Server server : servers) {
                totalIdleTime += server.getIdleTime();
            }
        } else {
            for (Server server : servers) {
                if (server.getClassServerid() == classServerId) {
                    totalIdleTime += server.getIdleTime();
                }
            }
        }

        return totalIdleTime;
    }

    /**
     * @return max idle time of all servers with the specified id
     */
    public double getMaxIdleTime(int classServerId) {
        double maxIdleTime = 0.0;

        if (classServerId == 0) {
            for (Server server : servers) {
                if (server.getMaxIdleTime() > maxIdleTime) {
                    maxIdleTime = server.getMaxIdleTime();
                }
            }
        } else {
            for (Server server : servers) {
                if (server.getClassServerid() == classServerId) {
                    if (server.getMaxIdleTime() > maxIdleTime) {
                        maxIdleTime = server.getMaxIdleTime();
                    }
                }
            }
        }

        return maxIdleTime;
    }

    /**
     * @return max queue size of all servers with the specified id
     */
    public int getMaxQueueSize(int classServerId) {
        int MaxQueueSize = 0;

        if (classServerId == 0) {
            for (Server server : servers) {
                if (server.getQueue().getMaxSize() > MaxQueueSize) {
                    MaxQueueSize = server.getQueue().getMaxSize();
                }
            }
        } else {
            for (Server server : servers) {
                if (server.getClassServerid() == classServerId) {
                    if (server.getQueue().getMaxSize() > MaxQueueSize) {
                        MaxQueueSize = server.getQueue().getMaxSize();
                    }
                }
            }
        }

        return MaxQueueSize;
    }
    // TODO InQueue Maintenance
    /**
     * @return in-Queue aircrafts of all servers with the specified id
     */
    public int getInQueueAircrafts(int classServerId) {
        int inQueueAircrafts = 0;

        if (classServerId == 0) {
            for (Server server : servers) {
                inQueueAircrafts = server.getQueue().size();
            }
        } else {
            for (Server server : servers) {
                if (server.getClassServerid() == classServerId) {
                    inQueueAircrafts += server.getQueue().size();
                }
            }
        }

        return inQueueAircrafts;
    }
}
