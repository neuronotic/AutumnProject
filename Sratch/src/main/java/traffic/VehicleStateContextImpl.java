package traffic;

import java.util.Iterator;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class VehicleStateContextImpl implements VehicleStateContext {


	private final Iterator<Cell> remainingItinerary;
	private final JourneyHistory history;
	private Cell location = new NullCell();

	@Inject
	public VehicleStateContextImpl(
			@Assisted final List<Cell> cellsInItinerary,
			final JourneyHistory history) {
				remainingItinerary = cellsInItinerary.iterator();
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
		if (cell.enter(vehicle)) {
			location.leave(vehicle);
			setLocation(cell);
			stepHistory();
		}
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
