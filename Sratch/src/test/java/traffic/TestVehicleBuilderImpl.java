package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestVehicleBuilderImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final VehicleFactory vehicleFactory = context.mock(VehicleFactory.class);
	private final VehicleStateContextBuilder vehicleStateContextBuilder = context.mock(VehicleStateContextBuilder.class);
	private final VehicleStateContext vehicleStateContext = context.mock(VehicleStateContext.class);
	private final VehicleJourneyState initialState = context.mock(VehicleJourneyState.class);
	private final VehicleStateFactory vehicleStateFactory = context.mock(VehicleStateFactory.class);
	private final Trip trip = context.mock(Trip.class);
	private final RoadNetwork roadNetwork = context.mock(RoadNetwork.class);
	private final Vehicle vehicle = context.mock(Vehicle.class);

	@Test
	public void makeCallsVehicleFactoryWithMadeStateContext() throws Exception {
		final String vehicleName = "myVehicle";

		context.checking(new Expectations() {
			{
				oneOf(vehicleStateContextBuilder).withRoadNetwork(roadNetwork); will(returnValue(vehicleStateContextBuilder));
				oneOf(vehicleStateContextBuilder).withTrip(trip); will(returnValue(vehicleStateContextBuilder));
				oneOf(vehicleStateContextBuilder).make(); will(returnValue(vehicleStateContext));
				oneOf(vehicleStateFactory).preJourneyState(); will(returnValue(initialState));
				oneOf(vehicleFactory).createVehicle(vehicleName, vehicleStateContext, initialState); will(returnValue(vehicle));
			}
		});

		final Vehicle createdVehicle = new VehicleBuilderImpl(vehicleFactory, vehicleStateContextBuilder, vehicleStateFactory)
			.withName(vehicleName)
			.withRoadNetwork(roadNetwork)
			.withTrip(trip)
			.make();

		assertThat(createdVehicle, equalTo(vehicle));
	}
}
