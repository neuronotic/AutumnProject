package traffic;

import static traffic.RoadNetworkToStringStyle.*;

import java.util.Iterator;

import traffic.endtoend.RoadUser;

public class RoadUserImpl implements RoadUser {
	private final Itinerary itinerary;
	private final Iterator<Cell> remainingJourney;
	private Cell location;
	private int journeyTime = 0;

	public RoadUserImpl(final Itinerary itinerary) {
		this.itinerary = itinerary;
		remainingJourney = itinerary.iterator();
	}

	@Override
	public Cell location() {
		return location;
	}

	@Override
	public void step() {
		final Cell cell = remainingJourney.next();
		cell.enter(this);
		location = cell;
		journeyTime++;
	}

	@Override
	public int journeyTime() {
		return journeyTime;
	}

	@Override
	public String toString() {
		return roadNetworkReflectionToString(this);
	}
}
