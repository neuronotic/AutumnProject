package traffic;


public interface VehicleFactory {
	Vehicle createVehicle(String vehicleName, Flow flow, VehicleStateContext vehicleStateContext, VehicleJourneyState vehicleJourneyState);
}
