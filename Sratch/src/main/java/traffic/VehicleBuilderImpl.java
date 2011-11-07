package traffic;

import com.google.inject.Inject;

public class VehicleBuilderImpl implements VehicleBuilder {

	private final VehicleFactory vehicleFactory;
	private final VehicleStateContextBuilder vehicleStateContextBuilder;
	private RoadNetwork roadNetwork;
	private Trip trip;
	private String vehicleName = "default name";
	private final VehicleStateFactory vehicleStateFactory;

	@Inject public VehicleBuilderImpl(
			final VehicleFactory vehicleFactory,
			final VehicleStateContextBuilder vehicleStateContextBuilder,
			final VehicleStateFactory vehicleStateFactory) {
				this.vehicleFactory = vehicleFactory;
				this.vehicleStateContextBuilder = vehicleStateContextBuilder;
				this.vehicleStateFactory = vehicleStateFactory;
	}

	@Override
	public Vehicle make() {
		return vehicleFactory.createVehicle(
				vehicleName,
				vehicleStateContextBuilder.withRoadNetwork(roadNetwork).withTrip(trip).make(),
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
