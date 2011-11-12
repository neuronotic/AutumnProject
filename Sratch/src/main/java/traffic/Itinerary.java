package traffic;

import java.util.List;

public interface Itinerary {
	List<Link> route();

	List<Cell> cells();
}
