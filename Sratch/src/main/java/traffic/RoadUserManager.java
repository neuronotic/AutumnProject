package traffic;

import traffic.endtoend.RoadUser;

public interface RoadUserManager {
	RoadUser roadUser(Itinerary itinerary);

	void step();
}
