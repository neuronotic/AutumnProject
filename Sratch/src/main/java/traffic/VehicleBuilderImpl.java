package traffic;

import com.google.inject.Inject;

public class VehicleBuilderImpl implements VehicleBuilder {

	private final VehicleFactory vehicleFactory;
	private final RouteFinderFactory routeFinderFactory;
	private final VehicleStateContextFactory vehicleStateContextFactory;
	private RoadNetwork roadNetwork;
	private Trip trip;
	private String vehicleName = "default name";
	private final VehicleStateFactory vehicleStateFactory;

	@Inject public VehicleBuilderImpl(
			final VehicleFactory vehicleFactory,
			final RouteFinderFactory routeFinderFactory,
			final VehicleStateContextFactory vehicleStateContextFactory,
			final VehicleStateFactory vehicleStateFactory) {
				this.vehicleFactory = vehicleFactory;
				this.routeFinderFactory = routeFinderFactory;
				this.vehicleStateContextFactory = vehicleStateContextFactory;
				this.vehicleStateFactory = vehicleStateFactory;
	}

	@Override
	public Vehicle make() {
		return vehicleFactory.createVehicle(
				vehicleName,
				vehicleStateContextFactory.createStateContext(routeFinderFactory.createShortestPathRouteFinder(roadNetwork).calculateItinerary(trip).iterator()),
				vehicleStateFactory.preJourneyState());
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
