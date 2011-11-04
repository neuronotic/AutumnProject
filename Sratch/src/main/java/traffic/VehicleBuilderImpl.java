package traffic;

import com.google.inject.Inject;

public class VehicleBuilderImpl implements VehicleBuilder {

	private final VehicleFactory vehicleFactory;
	private final RouteFinderFactory routeFinderFactory;
	private final VehicleStateContextFactory vehicleStateContextFactory;
	private RoadNetwork roadNetwork;
	private Trip trip;
	private String vehicleName = "default name";

	@Inject public VehicleBuilderImpl(
			final VehicleFactory vehicleFactory,
			final RouteFinderFactory routeFinderFactory,
			final VehicleStateContextFactory vehicleStateContextFactory) {
				this.vehicleFactory = vehicleFactory;
				this.routeFinderFactory = routeFinderFactory;
				this.vehicleStateContextFactory = vehicleStateContextFactory;
	}

	@Override
	public Vehicle make() {
		return vehicleFactory.createVehicle(
				vehicleName,
				vehicleStateContextFactory.createStateContext(routeFinderFactory.createShortestPathRouteFinder(roadNetwork).calculateItinerary(trip).iterator()));
		//return vehicleFactory.createVehicle(vehicleName, routeFinderFactory.createShortestPathRouteFinder(roadNetwork).calculateItinerary(trip));
	}

	@Override
	public VehicleBuilder withRoadNetwork(final RoadNetwork roadNetwork) {
		this.roadNetwork = roadNetwork;
		return this;
	}

	@Override
	public VehicleBuilder withTrip(final Trip trip) {
		this.trip = trip;
		return this;
	}

	@Override
	public VehicleBuilder withName(final String vehicleName) {
		this.vehicleName = vehicleName;
		return this;
	}
}
