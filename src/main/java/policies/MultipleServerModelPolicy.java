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
    public Server selectServer(List<Server>[]servers, int classEntityId) {
        int min = 0;
        Server ret = servers[0].get(0);
        
        if (classEntityId == 4){
            for (int i= 0; i<3; i++){
                for (Server server : servers[i]) {
                    if ((server.getDurability()/server.getMaxDurability() < ret.getDurability()/ret.getMaxDurability())) {
                        ret = server;    
                    }
                }
            }
        } else{ 
            for (int i = 1; i < servers[classEntityId-1].size(); i++) {
                if ((servers[classEntityId-1].get(i).getQueue().size() > servers[classEntityId-1].get(min).getQueue().size())
                        && !servers[classEntityId-1].get(i).isMaintenance()) {
                    min = i;
                }
            }
            ret = servers[classEntityId-1].get(min);
            //TODO
        }

        return ret;
    }
}
