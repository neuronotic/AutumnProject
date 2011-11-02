package traffic;


public interface VehicleFactory {
	Vehicle createVehicle(String vehicleName, Itinerary itinerary);
}
