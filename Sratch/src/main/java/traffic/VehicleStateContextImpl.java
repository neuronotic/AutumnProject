package traffic;

import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class VehicleStateContextImpl implements VehicleStateContext {
	@Inject Logger logger = Logger.getAnonymousLogger();


	private final ListIterator<Cell> remainingItinerary;
	private final JourneyHistoryBuilder journeyHistoryBuilder;
	private Cell currentLocation;
	private final MyEventBus journeyEndedEventBus;
	private final JourneyEndedMessageFactory journeyEndedMessageFactory;

	@Inject
	public VehicleStateContextImpl(
			final MyEventBus journeyEndedEventBus,
			final JourneyEndedMessageFactory journeyEndedMessageFactory,
			final NullCellFactory nullCellFactory,
			final JourneyHistoryBuilder journeyHistoryBuilder,
			@Assisted final List<Cell> cellsInItinerary) {
		this.journeyEndedEventBus = journeyEndedEventBus;
		this.journeyEndedMessageFactory = journeyEndedMessageFactory;
		remainingItinerary = cellsInItinerary.listIterator();
		this.journeyHistoryBuilder = journeyHistoryBuilder;
		currentLocation = nullCellFactory.createNullCell();
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
		logger.info(String.format("MOVE %s", vehicle));
		final Cell cell = nextCellInItinerary();
		if (cell.enter(vehicle)) {
			leaveCurrentLocationAndUpdateTo(cell);
			journeyHistoryBuilder.cellEntered(cell);
		} else {
			remainingItinerary.previous();
		}
	}

	private void leaveCurrentLocationAndUpdateTo(final Cell cell) {
		//logger.info(String.format("---changeLocation of %s to %s", vehicle, cell));
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
		//logger.info(String.format("journey ended for %s", vehicle));
		leaveCurrentLocation();
		journeyHistoryBuilder.noteEndTime();
		journeyEndedEventBus.post(journeyEndedMessageFactory.create(vehicle, journeyHistoryBuilder.make(vehicle)));
	}

	@Override
	public void subscribeToJourneyEndNotification(final Object subscriber) {
		//logger.info(String.format("subscription to JourneyEndNotification by", subscriber.getClass()));
		journeyEndedEventBus.register(subscriber);
	}
}
