package traffic;

import java.util.List;

public interface JunctionOccupancyFactory {

	JunctionOccupancy create(
			Junction junction,
			Occupancy occupancy,
			List<LinkOccupancy> linkOccupancies);
}
