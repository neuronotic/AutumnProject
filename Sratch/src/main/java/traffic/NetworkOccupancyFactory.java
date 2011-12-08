package traffic;

import java.util.Collection;

public interface NetworkOccupancyFactory {

	NetworkOccupancy create(Collection<JunctionOccupancy> junctionOccupancies);

}
