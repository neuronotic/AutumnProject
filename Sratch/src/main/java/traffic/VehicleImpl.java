package traffic;

import java.util.Iterator;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

class VehicleImpl implements Vehicle {

	private final String name;
	private final VehicleStateContext context;

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
		context = new VehicleStateContextImpl(this, remainingItinerary, history);
	}

	@Override
	public Cell location() {
		return context.location();
	}

	@Override
	public void step() {
		context.step();
	}

	@Override
	public int journeyTime() {
		return context.journeyTime();
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
