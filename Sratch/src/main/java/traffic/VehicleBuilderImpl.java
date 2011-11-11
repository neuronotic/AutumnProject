package traffic;

import com.google.inject.Inject;

public class VehicleBuilderImpl implements VehicleBuilder {

	private final VehicleFactory vehicleFactory;
	private String vehicleName = "default name";
	private final VehicleStateFactory vehicleStateFactory;
	private Itinerary itinerary;
	private final VehicleStateContextFactory vehicleStateContextFactory;

	@Inject public VehicleBuilderImpl(
			final VehicleFactory vehicleFactory,
			final VehicleStateContextFactory vehicleStateContextFactory,
			final VehicleStateFactory vehicleStateFactory) {
				this.vehicleFactory = vehicleFactory;
				this.vehicleStateContextFactory = vehicleStateContextFactory;
				this.vehicleStateFactory = vehicleStateFactory;
	}

	@Override
	public Vehicle make() {
		return vehicleFactory.createVehicle(
				vehicleName,
				vehicleStateContextFactory.createStateContext(itinerary.cells()),
				vehicleStateFactory.preJourneyState());
	}

	@Override
	public VehicleBuilder withName(final String vehicleName) {
		this.vehicleName = vehicleName;
		return this;
	}

	@Override
	public VehicleBuilder withItinerary(final Itinerary itinerary) {
		this.itinerary = itinerary;
		return this;
	}
}
