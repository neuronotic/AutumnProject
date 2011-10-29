package traffic;

import traffic.endtoend.RoadUser;

public class RoadUserImpl implements RoadUser {
	private final Itinerary itinerary;
	private Cell location;

	public RoadUserImpl(final Itinerary itinerary) {
		this.itinerary = itinerary;
	}

	@Override
	public Cell location() {
		return location;
	}

	@Override
	public String toString() {
		return String.format("road user located at %s", location);
	}

	@Override
	public void step() {
		final Cell cell = itinerary.iterator().next();
		cell.enter(this);
		location = cell;
	}
}
