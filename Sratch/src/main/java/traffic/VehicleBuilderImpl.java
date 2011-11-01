package traffic;

import com.google.inject.Inject;

public class VehicleBuilderImpl implements VehicleBuilder {

	private final VehicleFactory vehicleFactory;
	private final RouteFinderFactory routeFinderFactory;
	private RoadNetwork roadNetwork;
	private Trip trip;

	@Inject public VehicleBuilderImpl(
			final VehicleFactory vehicleFactory,
			final RouteFinderFactory routeFinderFactory) {
				this.vehicleFactory = vehicleFactory;
				this.routeFinderFactory = routeFinderFactory;
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
	public Vehicle make() {
		return vehicleFactory.createVehicle(routeFinderFactory.createShortestPathRouteFinder(roadNetwork).calculateItinerary(trip));
	}
}
