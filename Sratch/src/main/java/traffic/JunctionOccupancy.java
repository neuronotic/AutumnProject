package traffic;

import java.util.Collection;

public interface JunctionOccupancy {

	Junction junction();

	int totalOccupancy();

	int totalCapacity();

	Collection<LinkOccupancy> incomingLinkOccupancies();

}
