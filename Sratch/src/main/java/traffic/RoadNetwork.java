package traffic;


public interface RoadNetwork {
	void step();

	Segment shortestRoute(Junction origin, Junction destination);
}
