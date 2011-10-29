package traffic;

import traffic.endtoend.RoadUser;

public class RoadUserImpl implements RoadUser {
	private final Itinerary itinerary;
	private Junction location;

	public RoadUserImpl(final Itinerary itinerary) {
		this.itinerary = itinerary;
	}

	@Override
	public Junction location() {
		return location;
	}

	@Override
	public String toString() {
		return String.format("road user located at %s", location);
	}

	@Override
	public void step() {
		final Junction origin = itinerary.origin();
		origin.enter(this);
		location = origin;
	}
}
