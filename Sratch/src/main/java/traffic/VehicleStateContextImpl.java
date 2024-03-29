package traffic;

import java.util.ListIterator;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.name.Named;

public class VehicleStateContextImpl implements VehicleStateContext {
	@Inject Logger logger = Logger.getAnonymousLogger();


	private final ListIterator<Cell> remainingItinerary;
	private final JourneyHistoryBuilder journeyHistoryBuilder;
	private Cell currentLocation;
	private final MyEventBus eventBus;
	private final JourneyEndedMessageFactory journeyEndedMessageFactory;

	@Inject
	public VehicleStateContextImpl(
			final MyEventBus eventBus,
			final JourneyEndedMessageFactory journeyEndedMessageFactory,
			final JourneyHistoryBuilder journeyHistoryBuilder,
			@Named("NullCell") final Cell nullCell,
			@Assisted final Itinerary itinerary) {
		this.eventBus = eventBus;
		this.journeyEndedMessageFactory = journeyEndedMessageFactory;
		remainingItinerary = itinerary.cells().listIterator();
		this.journeyHistoryBuilder = journeyHistoryBuilder;
		currentLocation = nullCell;

	}

	@Override
	public Cell location() {
		return currentLocation;
	}

	@Override
	public SimulationTime journeyTime() {
		return journeyHistoryBuilder.journeyTime();
	}

	@Override
	public boolean hasJourneyRemaining() {
		return remainingItinerary.hasNext();
	}

	@Override
	public void move(final Vehicle vehicle) {
		//logger.info(String.format("MOVE %s", vehicle));
		final Cell cell = nextCellInItinerary();
		if (cell.enter(vehicle)) {
			leaveCurrentLocationAndUpdateTo(vehicle, cell);
			journeyHistoryBuilder.cellEntered(cell);
		} else {
			//logger.info(String.format("----change location FAILED for %s to %s", vehicle, cell));
			remainingItinerary.previous();
		}
	}

	private void leaveCurrentLocationAndUpdateTo(final Vehicle vehicle, final Cell cell) {
		//logger.info(String.format("---changeLocation SUCCESS of %s to %s", vehicle, cell));
		leaveCurrentLocation();
		currentLocation = cell;
	}

	private void leaveCurrentLocation() {
		currentLocation.leave();
	}

	private Cell nextCellInItinerary() {
		return remainingItinerary.next();
	}

	@Override
	public void journeyEnded(final Vehicle vehicle) {
		//logger.info(String.format("__JOURNEY ENDED__ for %s", vehicle));
		leaveCurrentLocation();
		journeyHistoryBuilder.noteEndTime();
		eventBus.post(journeyEndedMessageFactory.create(vehicle, journeyHistoryBuilder.make(vehicle)));
	}
}
