package traffic;

import java.util.Iterator;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

class VehicleImpl implements Vehicle {

	@Inject Logger logger = Logger.getAnonymousLogger();

	private final Iterator<Cell> remainingItinerary;
	private Cell location;
	private final JourneyHistory history;
	private final String name;

	@Inject VehicleImpl(
			@Assisted final String name,
			@Assisted final Itinerary remainingItinerary,
			final JourneyHistory history) {
		this(name, remainingItinerary.iterator(), history);
	}

	VehicleImpl(final String name,
			final Iterator<Cell> remainingItinerary,
			final JourneyHistory history) {
		this.name = name;
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
		logger.info(String.format("Vehicle entered %s", location));
	}

	@Override
	public int journeyTime() {
		return history.journeyTime();
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String toString() {
		return String.format("Vehicle %s located at %s", name(), location());
	}
}
