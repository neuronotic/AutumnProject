package traffic;


public interface RoadNetwork {
	Segment shortestRoute(Junction origin, Junction destination);
}
