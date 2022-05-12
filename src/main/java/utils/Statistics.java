package utils;

import java.util.List;
import resources.Server;

public class Statistics {
    private List<Server> servers;

    // Entity statistics
    private final int entityClassesNumber = 5;

    private final String[] entityHeader = { "General", "Light Aircraft", "Mid Weight Aircraft", "Heavy Aircraft",
            "Maintenance" };
    private int[] idCount = new int[entityClassesNumber];
    private double[] totalWaitingTime = new double[entityClassesNumber];
    private double[] maxWaitingTime = new double[entityClassesNumber];
    private double[] totalTransitTime = new double[entityClassesNumber];
    private double[] maxTransitTime = new double[entityClassesNumber];

    public Statistics(List<Server> servers) {
        this.servers = servers;
    }

    public String getClassEntityName(int classEntityId) {
        return entityHeader[classEntityId];
    }

    public int getIdCount(int classEntityId) {
        return idCount[classEntityId];
    }

    public void addIdCount(int classEntityId) {
        this.idCount[classEntityId]++;
    }

    public double getTotalWaitingTime(int classEntityId) {
        return totalWaitingTime[classEntityId];
    }

    public void accumulateWaitingTime(double WaitingTime, int classEntityId) {
        this.totalWaitingTime[classEntityId] += WaitingTime;
    }

    public double getMaxWaitingTime(int classEntityId) {
        return maxWaitingTime[classEntityId];
    }

    public void setMaxWaitingTime(double maxWaitingTime, int classEntityId) {
        this.maxWaitingTime[classEntityId] = maxWaitingTime;
    }

    public double getTotalTransitTime(int classEntityId) {
        return totalTransitTime[classEntityId];
    }

    public void accumulateTransitTime(double TransitTime, int classEntityId) {
        this.totalTransitTime[classEntityId] += TransitTime;
    }

    public double getMaxTransitTime(int classEntityId) {
        return maxTransitTime[classEntityId];
    }

    public void setMaxTransitTime(double maxTransitTime, int classEntityId) {
        this.maxTransitTime[classEntityId] = maxTransitTime;
    }

    // Server statistics discriminated by class
    private final String[] serverHeader = { "General", "Light Server", "Mid Weight Server", "Heavy Server" };
    private final int[] serverAmounts = { 9, 3, 4, 2 };

    public int getServerAmount(int classServerId) {
        return serverAmounts[classServerId];
    }

    public String getClassServerName(int classServerId) {
        return serverHeader[classServerId];
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
}
