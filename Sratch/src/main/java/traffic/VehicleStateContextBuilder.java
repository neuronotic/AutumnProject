package traffic;


public interface VehicleStateContextBuilder {

	VehicleStateContextBuilder withRoadNetwork(RoadNetwork roadNetwork);
	VehicleStateContextBuilder withTrip(Trip trip);

	VehicleStateContext make();


}
