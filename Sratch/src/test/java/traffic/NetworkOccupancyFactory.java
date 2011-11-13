package traffic;

import java.util.Set;

public interface NetworkOccupancyFactory {

	NetworkOccupancy create(Set<JunctionOccupancy> junctionOccupancies);

}
