package traffic;

import traffic.endtoend.RoadUser;

public interface RoadNetwork {
	void addRoadUser(RoadUser roadUser);

	void step();

	Segment shortestRoute(Junction origin, Junction destination);
}
