
package policies;

import java.util.List;
import resources.Server;

public interface ServerSelectionPolicy {
    /**
     * Select a server from a list of servers using the policy
     * defined in the underlying implementation
     *
     * @param servers The list of servers to choose one among them.
     * @return The selected server by the policy.
     */
    Server selectServer(List<Server>servers, int classEntityId);

}