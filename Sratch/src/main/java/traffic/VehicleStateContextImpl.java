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
	public void setLocation(final Cell cell) {
		location = cell;
	}

	@Override
	public void stepHistory() {
		history.stepped();
	}

	@Override
	public Cell nextCellInItinerary() {
		return remainingItinerary.next();
	}

	@Override
	public boolean hasNext() {
		return remainingItinerary.hasNext();
	}
}
