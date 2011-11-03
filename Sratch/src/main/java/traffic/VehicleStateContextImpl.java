package traffic;

import java.util.Iterator;
import java.util.logging.Logger;

import com.google.inject.Inject;

public class VehicleStateContextImpl implements VehicleStateContext {
	@Inject Logger logger = Logger.getAnonymousLogger();

	private final Vehicle vehicle;
	private final Iterator<Cell> remainingItinerary;
	private final JourneyHistory history;
	private Cell location;

	public VehicleStateContextImpl(
			final Vehicle vehicle,
			final Iterator<Cell> remainingItinerary,
			final JourneyHistory history) {
				this.vehicle = vehicle;
				this.remainingItinerary = remainingItinerary;
				this.history = history;
	}

	@Override
	public void step() {
		final Cell cell = remainingItinerary.next();
		cell.enter(vehicle);
		location = cell;
		history.stepped();
		logger.info(String.format("Vehicle entered %s", location));
	}

	@Override
	public Cell location() {
		return location;
	}

	@Override
	public int journeyTime() {
		return history.journeyTime();
	}

}
