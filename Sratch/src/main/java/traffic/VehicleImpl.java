package traffic;

import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

class VehicleImpl implements Vehicle {
	@Inject Logger logger = Logger.getAnonymousLogger();
	private final String name;
	private final VehicleStateContext stateContext;
	private VehicleJourneyState journeyState;

	@Inject VehicleImpl(
			@Assisted final String name,
			@Assisted final VehicleStateContext vehicleStateContext,
			@Assisted final VehicleJourneyState journeyState
			) {
		this.name = name;
		stateContext = vehicleStateContext;
		this.journeyState = journeyState;
		//logger.info(String.format("CREATED %s", this));
	}

	@Override
	public Cell location() {
		return stateContext.location();
	}

	@Override
	public void step() {
		//logger.info(String.format("step: Vehicle %s located at %s", name(), location()));
		journeyState = journeyState.step(this, stateContext);
	}

	@Override
	public SimulationTime journeyTime() {
		return stateContext.journeyTime();
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String toString() {
		return String.format("%s located at %s", name(), location());
	}

	@Override
	public void startJourney() {
		stateContext.startJourney(this);
	}
}
