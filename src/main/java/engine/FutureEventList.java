package engine;

import java.util.List;
import java.util.Comparator;
import events.Event;
import java.util.ArrayList;

public class FutureEventList {
    private List<Event> felImpl;
    private Comparator<Event> comparator;

    public FutureEventList() {
        this.felImpl = new ArrayList<Event>();
        this.comparator = Event.getComparator();
    }

    public Event getImminent() {
        return this.felImpl.remove(0);
    }

    public void insert(Event event) {
        this.felImpl.add(event);
        this.felImpl.sort(this.comparator);
    }

    @Override
    public String toString() {
        String ret = "============================================================== F E L ==============================================================\n";

        for (Event event : felImpl) {
            ret += event.toString() + "\n";
        }

        ret += "\n***********************************************************************************************************************************\n\n";

        return ret;
    }
}