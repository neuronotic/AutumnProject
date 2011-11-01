package traffic;

public interface VehicleBuilder {

	VehicleBuilder withRoadNetwork(RoadNetwork roadNetwork);

	VehicleBuilder withTrip(Trip trip);

	Vehicle make();

}
