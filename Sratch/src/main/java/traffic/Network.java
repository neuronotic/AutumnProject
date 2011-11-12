package traffic;

import java.util.Collection;
import java.util.List;


public interface Network {
	List<Segment> segments();

	Collection<Junction> junctions();

	void step();
}
