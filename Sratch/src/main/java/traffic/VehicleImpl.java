package traffic;

import static traffic.RoadNetworkToStringStyle.*;

import java.util.Iterator;


public class VehicleImpl implements Vehicle {
	private final Iterator<Cell> remainingItinerary;
	private Cell location;
	private final JourneyHistory history;

	public VehicleImpl(
			final Iterator<Cell> remainingItinerary,
			final JourneyHistory history) {
		this.remainingItinerary = remainingItinerary;
		this.history = history;
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
		history.stepped();
	}

	@Override
	public int journeyTime() {
		return history.journeyTime();
	}

	@Override
	public String toString() {
		return roadNetworkReflectionToString(this);
	}
}
