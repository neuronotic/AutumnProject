package traffic;

public class VehicleJourneyUnstartedState implements VehicleJourneyState {

	private final VehicleStateFactory vehicleStateFactory;

	public VehicleJourneyUnstartedState(final VehicleStateFactory vehicleStateFactory) {
		this.vehicleStateFactory = vehicleStateFactory;
	}

	@Override
	public VehicleJourneyState step(final Vehicle vehicle, final VehicleStateContext stateContext) {
		return vehicleStateFactory.duringJourneyState().step(vehicle, stateContext);
	}

}
