package traffic;

public interface VehicleJourneyState {

	VehicleJourneyState step(Vehicle vehicle, VehicleStateContext stateContext);

}
