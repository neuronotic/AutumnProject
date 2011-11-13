package traffic;

import java.util.Set;

public interface JunctionOccupancyFactory {

	JunctionOccupancy create(
			Junction junction,
			Occupancy occupancy,
			Set<LinkOccupancy> linkOccupancies);
}
