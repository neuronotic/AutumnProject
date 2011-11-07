package traffic;

public class VehicleJourneyEndedState implements VehicleJourneyState {

	@Override
	public VehicleJourneyState step(final Vehicle vehicle,
			final VehicleStateContext stateContext) {
		stateContext.journeyEnded(vehicle);
		return this;
	}

}
