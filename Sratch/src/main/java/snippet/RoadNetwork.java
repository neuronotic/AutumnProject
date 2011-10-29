package snippet;

import traffic.endtoend.RoadUser;

public interface RoadNetwork {
	void addRoadUser(RoadUser roadUser);

	void step();
}
