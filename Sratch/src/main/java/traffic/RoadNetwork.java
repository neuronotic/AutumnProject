package traffic;

import java.util.Collection;
import java.util.List;


public interface RoadNetwork {
	List<Segment> segments();

	Collection<Junction> junctions();
}
