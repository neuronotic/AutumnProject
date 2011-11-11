package traffic;

import com.google.inject.Inject;

public class VehicleBuilderImpl implements VehicleBuilder {

	private final VehicleFactory vehicleFactory;
	private String vehicleName = "default name";
	private final VehicleStateFactory vehicleStateFactory;
	private Itinerary itinerary;
	private final VehicleStateContextFactory vehicleStateContextFactory;
	private final MyEventBus eventBus;
	private final JourneyStartedMessageFactory journeyStartedMessageFactory;

	@Inject public VehicleBuilderImpl(
			final MyEventBus eventBus,
			final JourneyStartedMessageFactory journeyStartedMessageFactory,
			final VehicleFactory vehicleFactory,
			final VehicleStateContextFactory vehicleStateContextFactory,
			final VehicleStateFactory vehicleStateFactory) {
				this.eventBus = eventBus;
				this.journeyStartedMessageFactory = journeyStartedMessageFactory;
				this.vehicleFactory = vehicleFactory;
				this.vehicleStateContextFactory = vehicleStateContextFactory;
				this.vehicleStateFactory = vehicleStateFactory;
	}

	@Override
	public Vehicle make() {
		final Vehicle vehicle = vehicleFactory.createVehicle(
				vehicleName,
				vehicleStateContextFactory.createStateContext(itinerary.cells()),
				vehicleStateFactory.duringJourneyState());//.preJourneyState());
		eventBus.post(journeyStartedMessageFactory.create(vehicle));
		return vehicle;
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
