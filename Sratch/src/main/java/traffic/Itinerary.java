package traffic;

import java.util.List;

public interface Itinerary {
	List<Segment> route();

	List<Cell> cells();
}
