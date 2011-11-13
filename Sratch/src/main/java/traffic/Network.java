package traffic;

import java.util.Collection;
import java.util.List;


public interface Network {
	List<Link> links();

	Collection<Junction> junctions();

	void step();

	NetworkOccupancy occupancy();
}
