package traffic;

import static java.util.Arrays.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestVehicleStateContextBuilderImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final RouteFinderFactory routeFinderFactory = context.mock(RouteFinderFactory.class);
	private final VehicleStateContextFactory vehicleStateContextFactory = context.mock(VehicleStateContextFactory.class);
	private final VehicleStateContext vehicleStateContext = context.mock(VehicleStateContext.class);
	private final Trip trip = context.mock(Trip.class);
	private final RoadNetwork roadNetwork = context.mock(RoadNetwork.class);
	private final Itinerary itinerary = context.mock(Itinerary.class);
	private final List<Cell> cells = asList(context.mock(Cell.class));
	private final RouteFinder routeFinder = context.mock(RouteFinder.class);

	@Test
	public void makeReturnsContextObjectWith() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(routeFinderFactory).createShortestPathRouteFinder(roadNetwork); will(returnValue(routeFinder));
				oneOf(routeFinder).calculateItinerary(trip); will(returnValue(itinerary));
				oneOf(itinerary).cells(); will(returnValue(cells));
				oneOf(vehicleStateContextFactory).createStateContext(cells); will(returnValue(vehicleStateContext));
			}
		});

		final VehicleStateContext madeStateContext =
				new VehicleStateContextBuilderImpl(vehicleStateContextFactory, routeFinderFactory)
					.withRoadNetwork(roadNetwork)
					.withTrip(trip)
					.make();

		assertThat(madeStateContext, is(vehicleStateContext));
	}

}
