package traffic;

import java.util.Iterator;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class VehicleStateContextImpl implements VehicleStateContext {


	private final Iterator<Cell> remainingItinerary;
	private final JourneyHistory history;
	private Cell location;

	@Inject
	public VehicleStateContextImpl(
			@Assisted final Iterator<Cell> remainingItinerary,
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
	public boolean hasJourneyRemaining() {
		return remainingItinerary.hasNext();
	}

	@Override
	public void move(final Vehicle vehicle) {
		final Cell cell = nextCellInItinerary();
		cell.enter(vehicle);
		setLocation(cell);
		stepHistory();
	}

	private void setLocation(final Cell cell) {
		location = cell;
	}

	private void stepHistory() {
		history.stepped();
	}

	private Cell nextCellInItinerary() {
		return remainingItinerary.next();
	}
}
