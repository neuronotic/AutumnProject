package traffic;

import java.util.Iterator;
import java.util.logging.Logger;

import com.google.inject.Inject;

public class VehicleStateContextImpl implements VehicleStateContext {
	@Inject Logger logger = Logger.getAnonymousLogger();

	private final Iterator<Cell> remainingItinerary;
	private final JourneyHistory history;
	private Cell location;

	public VehicleStateContextImpl(
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
	public int journeyTime() {
		return history.journeyTime();
	}

	@Override
	public void setLocation(final Cell cell) {
		location = cell;
	}

	@Override
	public void logStep() {
		logger.info(String.format("Vehicle located at %s", location()));
	}

	@Override
	public void stepHistory() {
		history.stepped();
	}

	@Override
	public Cell nextCellInItinerary() {
		return remainingItinerary.next();
	}
}
