package policies;

import java.util.List;
import resources.Server;

public class MultipleServerModelPolicy implements ServerSelectionPolicy {
    private static MultipleServerModelPolicy policy;

    private MultipleServerModelPolicy() {
        // Empty constructor
    }

    public static MultipleServerModelPolicy getInstance() {
        if (MultipleServerModelPolicy.policy == null)
            MultipleServerModelPolicy.policy = new MultipleServerModelPolicy();

        return MultipleServerModelPolicy.policy;
    }

    @Override
    public Server selectServer(List<Server> servers, int classEntityId) {
        Server aux = null, ret = null;
        int inicial = 0;

        //Control for having element to compare not in maintenance
        for (int i = 0; i < servers.size() || ret == null; i++) {
            ret = aux = servers.get(inicial);
            if(ret.isMaintenance()) {
                inicial++;
            } else {
                break;
            }
        }

        // Selection for maintenance
        if (classEntityId == 4) {
            for (int j = 0; j < servers.size(); j++) {
                Server server = servers.get(j);

                if ((server.getDurability() / server.getMaxDurability() < ret.getDurability()
                        / ret.getMaxDurability()) && server.isMaintenance() == false) {
                    ret = server;
                }
            }
        // Selection for Airstrips
        } else {
            for (int i = 0; i < servers.size(); i++) {
                Server server = servers.get(i);
                // Free server case
                if (!(server.isBusy()) && (server.getClassServerid() == classEntityId)) {
                    return server;
                }
                if (!server.isMaintenance()) {
                    // Server with smallest queue size and the airstrip respective type
                    if ((server.getQueue().size() < ret.getQueue().size())
                            && (server.getClassServerid() == classEntityId)) {
                        ret = server;
                    }
                    // Server with smallest queue size
                    if ((server.getQueue().size() < aux.getQueue().size())
                            && !(server.isMaintenance())) {
                        aux = server;
                    }
                }
            }
            // Control for when no server from the aistrip type is available
            if (ret.getClassServerid() != classEntityId) {
                ret = aux;
            }
        }

        return ret;
    }
}
