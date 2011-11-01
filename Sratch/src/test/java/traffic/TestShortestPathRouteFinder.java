package traffic;

import static org.hamcrest.MatcherAssert.*;
import static traffic.ItineraryMatchers.*;
import static traffic.MyJMockActions.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestShortestPathRouteFinder {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Segment segment = context.mock(Segment.class);
	private final Trip trip = context.mock(Trip.class);
	private final RoadNetwork roadNetwork = context.mock(RoadNetwork.class);

	@Test
	public void calculateItineraryReturnsSegmentsreturnedByCallingRouteOnRoadNetwork() {
		final RouteFinder routeFinder = new ShortestPathRouteFinder(roadNetwork);

		context.checking(new Expectations() {
			{
				oneOf(roadNetwork).route(trip); will(returnList(segment));
			}
		});

		assertThat(routeFinder.calculateItinerary(trip), itineraryRouteIs(segment));
	}
}
