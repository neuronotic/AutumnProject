package traffic;

import java.util.List;
import java.util.ListIterator;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class VehicleStateContextImpl implements VehicleStateContext {


	private final ListIterator<Cell> remainingItinerary;
	private final JourneyHistory history;
	private Cell location;
	private final NullCellFactory nullCellFactory;

	@Inject
	public VehicleStateContextImpl(
			final NullCellFactory nullCellFactory,
			@Assisted final List<Cell> cellsInItinerary,
			final JourneyHistory history) {
				this.nullCellFactory = nullCellFactory;
				remainingItinerary = cellsInItinerary.listIterator();
				this.history = history;
				location = nullCellFactory.createNullCell();
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
		} else {
			remainingItinerary.previous();
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

	@Override
	public void journeyEnded(final Vehicle vehicle) {
		location.leave(vehicle);
		location = nullCellFactory.createNullCell();
	}
}
