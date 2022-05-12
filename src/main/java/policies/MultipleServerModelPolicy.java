package policies;

import java.util.List;

import resources.CustomQueue;
import resources.LightAirstrip;
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
        Server nulo = new LightAirstrip(new CustomQueue(1000));
        Server aux = nulo;
        Server ret = nulo;

        if (classEntityId == 4) {
            for (int index = 0; index < servers.size(); index++) {
                if ((servers.get(index).getDurability() / servers.get(index).getMaxDurability() < ret.getDurability()
                        / ret.getMaxDurability()) && servers.get(index).isMaintenance() == false) {
                    ret = servers.get(index);
                }
            }
        } else {
            for (int i = 0; i < servers.size(); i++) {
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
            if (ret.equals(nulo)) {
                ret = aux;
            }
        }

        return ret;
    }
}
