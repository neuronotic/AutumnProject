package traffic;

import static traffic.RoadNetworkToStringStyle.*;

import java.util.Iterator;


public class RoadUserImpl implements RoadUser {
	private final Iterator<Cell> remainingItinerary;
	private Cell location;
	private int journeyTime = 0;

	public RoadUserImpl(final Iterator<Cell> remainingItinerary) {
		this.remainingItinerary = remainingItinerary;
	}

	@Override
	public Cell location() {
		return location;
	}

	@Override
	public void step() {
		final Cell cell = remainingItinerary.next();
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
