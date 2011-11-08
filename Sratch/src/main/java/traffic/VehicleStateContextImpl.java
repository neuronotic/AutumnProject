package traffic;

import java.util.List;
import java.util.ListIterator;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class VehicleStateContextImpl implements VehicleStateContext {


	private final ListIterator<Cell> remainingItinerary;
	private JourneyHistoryBuilder journeyHistoryBuilder;
	private final JourneyStepper journeyStepper;
	private Cell location;
	private final NullCellFactory nullCellFactory;

	@Inject
	public VehicleStateContextImpl(
			final NullCellFactory nullCellFactory,
			@Assisted final List<Cell> cellsInItinerary,
			final JourneyStepper journeyStepper) {
				this.nullCellFactory = nullCellFactory;
				remainingItinerary = cellsInItinerary.listIterator();
				this.journeyStepper = journeyStepper;
				location = nullCellFactory.createNullCell();
	}

	@Override
	public Cell location() {
		return location;
	}

	@Override
	public int journeyTime() {
		return journeyStepper.journeyTime();
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
			location = cell;
			//journeyHistoryBuilder.withCellEntryTime(cell, SimulationTime.timeNow())
			journeyStepper.stepped();
		} else {
			remainingItinerary.previous();
		}
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
