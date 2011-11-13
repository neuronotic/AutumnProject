package traffic;

import java.util.List;

public interface NetworkOccupancyFactory {

	NetworkOccupancy create(List<JunctionOccupancy> junctionOccupancies);

}
