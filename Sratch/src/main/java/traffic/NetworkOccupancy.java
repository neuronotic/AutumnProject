package traffic;

import java.util.Collection;

public interface NetworkOccupancy {

	Collection<JunctionOccupancy> junctionOccupancies();

	JunctionOccupancy occupancyFor(Junction junction);

	LinkOccupancy occupancyFor(Link link);

}
