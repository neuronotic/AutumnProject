package traffic;

import java.util.Iterator;

public interface Itinerary extends Iterable<Cell> {
	Segment route();

	Iterator<Cell> iterator();
}
