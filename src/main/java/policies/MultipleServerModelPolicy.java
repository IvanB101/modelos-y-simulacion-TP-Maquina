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
    public Server selectServer(List<Server> servers) {
        int min = 0;
        for (int i = 1; i < servers.size(); i++) {
            if ((servers.get(i).getQueue().size() > servers.get(min).getQueue().size())
                    && !servers.get(i).isMaintenance()) {
                min = i;
            }
        }
        return servers.get(min);
    }
}
