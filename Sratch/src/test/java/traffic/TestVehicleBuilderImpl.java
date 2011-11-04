package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Iterator;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestVehicleBuilderImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final RouteFinderFactory routeFinderFactory = context.mock(RouteFinderFactory.class);
	private final VehicleFactory vehicleFactory = context.mock(VehicleFactory.class);
	private final VehicleStateContextFactory vehicleStateContextFactory = context.mock(VehicleStateContextFactory.class);
	private final VehicleStateContext vehicleStateContext = context.mock(VehicleStateContext.class);
	private final Trip trip = context.mock(Trip.class);
	private final RoadNetwork roadNetwork = context.mock(RoadNetwork.class);
	private final Itinerary itinerary = context.mock(Itinerary.class);
	private final Iterator<Cell> iterator = context.mock(Iterator.class);
	private final Vehicle vehicle = context.mock(Vehicle.class);
	private final RouteFinder routeFinder = context.mock(RouteFinder.class);

	@Test
	public void makeCallsVehicleFactoryToCreateVehicleWithItineraryCreatedRouteFinder() throws Exception {
		final String vehicleName = "myVehicle";

		context.checking(new Expectations() {
			{
				oneOf(routeFinderFactory).createShortestPathRouteFinder(roadNetwork); will(returnValue(routeFinder));
				oneOf(routeFinder).calculateItinerary(trip); will(returnValue(itinerary));
				oneOf(itinerary).iterator(); will(returnValue(iterator));
				oneOf(vehicleStateContextFactory).createStateContext(iterator); will(returnValue(vehicleStateContext));
				oneOf(vehicleFactory).createVehicle(vehicleName, vehicleStateContext); will(returnValue(vehicle));
			}
		});

		final Vehicle createdVehicle = new VehicleBuilderImpl(vehicleFactory, routeFinderFactory, vehicleStateContextFactory)
			.withName(vehicleName)
			.withRoadNetwork(roadNetwork)
			.withTrip(trip)
			.make();

		assertThat(createdVehicle, equalTo(vehicle));
	}
}
