package traffic;

import traffic.endtoend.RoadUser;

public class RoadUserFactory {
	public static RoadUser roadUser(final Itinerary itinerary) {
		return new RoadUserImpl(itinerary);
	}
}
