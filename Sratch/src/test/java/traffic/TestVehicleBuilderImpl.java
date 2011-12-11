package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestVehicleBuilderImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final MyEventBus eventBus = context.mock(MyEventBus.class);
	private final JourneyStartedMessageFactory journeyStartedMessageFactory = context.mock(JourneyStartedMessageFactory.class);
	private final JourneyStartedMessage journeyStartedMessage = context.mock(JourneyStartedMessage.class);

	private final VehicleFactory vehicleFactory = context.mock(VehicleFactory.class);
	private final VehicleStateContextFactory vehicleStateContextFactory= context.mock(VehicleStateContextFactory.class);
	private final VehicleStateContext vehicleStateContext = context.mock(VehicleStateContext.class);
	private final VehicleJourneyState initialState = context.mock(VehicleJourneyState.class);
	private final VehicleStateFactory vehicleStateFactory = context.mock(VehicleStateFactory.class);
	private final Vehicle vehicle = context.mock(Vehicle.class);
	private final Itinerary itinerary = context.mock(Itinerary.class);
	private final Cell cell0 = context.mock(Cell.class);
	private final Flow flow = context.mock(Flow.class);
	private final Flow nullFlow = context.mock(Flow.class, "nullFlow");

	private final VehicleBuilder vehicleBuilder = new VehicleBuilderImpl(eventBus, nullFlow, journeyStartedMessageFactory, vehicleFactory, vehicleStateContextFactory, vehicleStateFactory);

	@Test
	public void makeCallsVehicleFactoryWithMadeStateContext() throws Exception {
		final String vehicleName = "myVehicle";

		context.checking(new Expectations() {
			{
				oneOf(journeyStartedMessageFactory).create(vehicle); will(returnValue(journeyStartedMessage));
				oneOf(eventBus).post(journeyStartedMessage);
				oneOf(vehicleStateContextFactory).createStateContext(itinerary); will(returnValue(vehicleStateContext));
				oneOf(vehicleStateFactory).duringJourneyState(); will(returnValue(initialState));
				//oneOf(vehicleStateFactory).preJourneyState(); will(returnValue(initialState));
				oneOf(vehicleFactory).createVehicle(vehicleName, flow, vehicleStateContext, initialState); will(returnValue(vehicle));
			}
		});

		final Vehicle createdVehicle = vehicleBuilder
			.withName(vehicleName)
			.withFlow(flow)
			.withItinerary(itinerary)
			.make();

		assertThat(createdVehicle, equalTo(vehicle));
	}

	@Test
	public void withNameReturnsInstanceItWasCalledOn() throws Exception {
		assertThat(vehicleBuilder.withName(null), is(vehicleBuilder));
	}

	@Test
	public void withItineraryReturnsInstanceItWasCalledOn() throws Exception {
		assertThat(vehicleBuilder.withItinerary(null), is(vehicleBuilder));
	}

}
