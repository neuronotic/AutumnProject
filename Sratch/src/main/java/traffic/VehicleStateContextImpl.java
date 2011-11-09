package traffic;

import java.util.List;
import java.util.ListIterator;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class VehicleStateContextImpl implements VehicleStateContext {


	private final ListIterator<Cell> remainingItinerary;
	private final JourneyHistoryBuilder history;
	private Cell location;
	private final NullCellFactory nullCellFactory;

	@Inject
	public VehicleStateContextImpl(
			final NullCellFactory nullCellFactory,
			@Assisted final List<Cell> cellsInItinerary,
			final JourneyHistoryBuilder history) {
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
	public SimulationTime journeyTime() {
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
			changeLocation(vehicle, cell);
			stepHistory();
		} else {
			remainingItinerary.previous();
		}
	}

	private void changeLocation(final Vehicle vehicle, final Cell cell) {
		location.leave(vehicle);
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
		changeLocation(vehicle, nullCellFactory.createNullCell());
	}
}
