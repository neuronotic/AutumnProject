package traffic;

import java.util.List;


public interface RoadNetwork {
	List<Segment> route(Junction origin, Junction destination);

	List<Segment> route(Trip trip);
}
