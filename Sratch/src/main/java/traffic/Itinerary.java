package traffic;

import java.util.Iterator;
import java.util.List;

public interface Itinerary extends Iterable<Cell> {
	List<Segment> route();

	Iterator<Cell> iterator();
}
