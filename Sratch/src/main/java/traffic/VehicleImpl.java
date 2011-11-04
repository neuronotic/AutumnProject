package traffic;

import java.util.Iterator;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

class VehicleImpl implements Vehicle {
	@Inject Logger logger = Logger.getAnonymousLogger();
	private final String name;
	private final VehicleStateContext stateContext;

	@Inject VehicleImpl(
			@Assisted final String name,
			@Assisted final Itinerary remainingItinerary,
			final JourneyHistory history) {
		this(name, remainingItinerary.iterator(), history, new VehicleStateContextImpl(remainingItinerary.iterator(), history));
	}

	VehicleImpl(final String name,
			final Iterator<Cell> remainingItinerary,
			final JourneyHistory history,
			final VehicleStateContext stateContext) {
		this.name = name;
		this.stateContext = stateContext;
	}

	@Override
	public Cell location() {
		return stateContext.location();
	}

	@Override
	public void step() {
		final Cell cell = stateContext.nextCellInItinerary();
		cell.enter(this);
		stateContext.setLocation(cell);
		stateContext.stepHistory();
		logger.info(String.format("Vehicle %s located at %s", name(), location()));
	}

	@Override
	public int journeyTime() {
		return stateContext.journeyTime();
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
