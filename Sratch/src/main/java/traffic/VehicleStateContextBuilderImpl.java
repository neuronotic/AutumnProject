package traffic;

import com.google.inject.Inject;


public class VehicleStateContextBuilderImpl implements
		VehicleStateContextBuilder {

//	.withRouteIterator(routeFinderFactory.createShortestPathRouteFinder(roadNetwork).calculateItinerary(trip).iterator()),

	private RoadNetwork roadNetwork;
	private Trip trip;
	private final VehicleStateContextFactory vehicleStateContextFactory;
	private final RouteFinderFactory routeFinderFactory;


	@Inject public VehicleStateContextBuilderImpl(
			final VehicleStateContextFactory vehicleStateContextFactory,
			final RouteFinderFactory routeFinderFactory) {
				this.vehicleStateContextFactory = vehicleStateContextFactory;
				this.routeFinderFactory = routeFinderFactory;
	}

	@Override
	public VehicleStateContext make() {
		return vehicleStateContextFactory.createStateContext(
				routeFinderFactory
					.createShortestPathRouteFinder(roadNetwork)
					.calculateItinerary(trip)
					.cells());
	}

	@Override
	public VehicleStateContextBuilder withRoadNetwork(final RoadNetwork roadNetwork) {
		this.roadNetwork = roadNetwork;
		return this;
	}

	@Override
	public VehicleStateContextBuilder withTrip(final Trip trip) {
		this.trip = trip;
		return this;
	}

}
