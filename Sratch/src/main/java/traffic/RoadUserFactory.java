package traffic;

import traffic.endtoend.RoadUser;

public interface RoadUserFactory {
	RoadUser createRoadUser(Itinerary itinerary);
}
