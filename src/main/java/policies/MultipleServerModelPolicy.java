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
        Server aux = servers.get(0), ret = servers.get(0);

        if (classEntityId == 4) {
            for (int index = 1; index < servers.size(); index++) {
                if ((servers.get(index).getDurability() / servers.get(index).getMaxDurability() < ret.getDurability()
                        / ret.getMaxDurability()) && servers.get(index).isMaintenance() == false) {
                    ret = servers.get(index);
                }
            }
        } else {
            for (int i = 0; i < servers.size(); i++) {
                if (!(servers.get(i).isBusy()) && servers.get(i).getClassServerid() == classEntityId) {
                    ret = servers.get(i);
                    break;
                }
                if ((servers.get(i).getQueue().size() < ret.getQueue().size())
                        && (servers.get(i).getClassServerid() == classEntityId)
                        && servers.get(i).isMaintenance() == false) {
                    ret = servers.get(i);
                }
                if ((servers.get(i).getQueue().size() < aux.getQueue().size())
                        && servers.get(i).isMaintenance() == false) {
                    aux = servers.get(i);
                }
            }
            if((ret == servers.get(0)) && (servers.get(0).getClassServerid() != classEntityId)) {
                ret = aux;
            }
        }

        return ret;
    }
}
