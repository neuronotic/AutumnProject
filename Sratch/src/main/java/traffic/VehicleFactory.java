package traffic;


public interface VehicleFactory {
	Vehicle createVehicle(String vehicleName, VehicleStateContext vehicleStateContext, VehicleJourneyState vehicleJourneyState);
}
