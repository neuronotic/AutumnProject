package traffic;

import java.util.Collection;


public interface Network {
	Collection<Link> links();

	Collection<Junction> junctions();

	void step();

	NetworkOccupancy occupancy();

	Link linkNamed(String linkName);
}
