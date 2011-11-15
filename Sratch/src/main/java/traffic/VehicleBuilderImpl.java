package traffic;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class VehicleBuilderImpl implements VehicleBuilder {

	private final VehicleFactory vehicleFactory;
	private String vehicleName = "default name";
	private final VehicleStateFactory vehicleStateFactory;
	private Itinerary itinerary;
	private final VehicleStateContextFactory vehicleStateContextFactory;
	private final MyEventBus eventBus;
	private final JourneyStartedMessageFactory journeyStartedMessageFactory;
	private Flow flow;

	@Inject public VehicleBuilderImpl(
			final MyEventBus eventBus,
			@Named("null") final Flow flow,
			final JourneyStartedMessageFactory journeyStartedMessageFactory,
			final VehicleFactory vehicleFactory,
			final VehicleStateContextFactory vehicleStateContextFactory,
			final VehicleStateFactory vehicleStateFactory) {
				this.eventBus = eventBus;
				this.flow = flow;
				this.journeyStartedMessageFactory = journeyStartedMessageFactory;
				this.vehicleFactory = vehicleFactory;
				this.vehicleStateContextFactory = vehicleStateContextFactory;
				this.vehicleStateFactory = vehicleStateFactory;
	}

	@Override
	public Vehicle make() {
		final Vehicle vehicle = vehicleFactory.createVehicle(
				vehicleName,
				flow,
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

	@Override
	public VehicleBuilder withFlow(final Flow flow) {
		this.flow = flow;
		return this;
	}
}
