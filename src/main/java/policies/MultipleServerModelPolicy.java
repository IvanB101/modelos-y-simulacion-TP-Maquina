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
        Server ret = servers.get(0);
        boolean entre=false;
        Server aux=null;
        if (!(servers.get(0).isMaintenance())){
            aux = servers.get(0);
        } else{
            aux=servers.get(1);
        }
        if (classEntityId == 4) {
            for (int j = 0; j < servers.size(); j++) {
                if ((servers.get(j).getDurability() / servers.get(j).getMaxDurability() < ret.getDurability()
                        / ret.getMaxDurability()) && servers.get(j).isMaintenance() == false) {
                    ret = servers.get(j);
                }
            }
        } else {
            for (int i = 0; i < servers.size(); i++) {
                if (!(servers.get(i).isBusy()) && servers.get(i).getClassServerid() == classEntityId) {
                    ret = servers.get(i);
                    entre=true;
                    break;
                }
                if (!(servers.get(i).isMaintenance())){
                    if((servers.get(i).getClassServerid()) == classEntityId){
                        if (!entre){
                            ret=servers.get(i);
                            entre=true;
                        }
                        if (servers.get(i).getQueue().size() < ret.getQueue().size()){
                            ret = servers.get(i);
                        }
                    }
                    if (servers.get(i).getQueue().size() < aux.getQueue().size()){
                        aux = servers.get(i);
                    }
                }
            }
            if(!entre){
                ret = aux;
            }
        }

        return ret;
    }
}
