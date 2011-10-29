package traffic;

import traffic.endtoend.RoadUser;

public class RoadUserImpl implements RoadUser {
	private final Itinerary itinerary;

	public RoadUserImpl(final Itinerary itinerary) {
		this.itinerary = itinerary;
	}

	@Override
	public Junction location() {
		return null;
	}
}
