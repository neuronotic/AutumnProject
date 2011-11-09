package traffic;

import java.util.logging.Logger;

import com.google.inject.Inject;

public class VehicleJourneyEndedState implements VehicleJourneyState {
	@Inject Logger logger = Logger.getAnonymousLogger();

	@Override
	public VehicleJourneyState step(final Vehicle vehicle,
			final VehicleStateContext stateContext) {
		//logger.info(String.format("JOURNEY ENDED STATE for Vehicle %s", vehicle));
		stateContext.journeyEnded(vehicle);
		return this;
	}

}
