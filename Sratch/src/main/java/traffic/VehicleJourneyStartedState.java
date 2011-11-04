package traffic;

public class VehicleJourneyStartedState implements VehicleJourneyState {

	private final VehicleStateFactory vehicleStateFactory;

	public VehicleJourneyStartedState(
			final VehicleStateFactory vehicleStateFactory) {
				this.vehicleStateFactory = vehicleStateFactory;
	}

	@Override
	public VehicleJourneyState step(final Vehicle vehicle, final VehicleStateContext stateContext) {
		if (stateContext.hasNext()) {
			final Cell cell = stateContext.nextCellInItinerary();
			cell.enter(vehicle);
			stateContext.setLocation(cell);
			stateContext.stepHistory();
			return this;
		}
		return vehicleStateFactory.postJourneyState().step(vehicle, stateContext);
	}

}
