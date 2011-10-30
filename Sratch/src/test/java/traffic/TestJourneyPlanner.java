package traffic;

import static org.hamcrest.MatcherAssert.*;
import static traffic.ItineraryMatchers.*;
import static traffic.JourneyPlanner.*;
import static traffic.MyJMockActions.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestJourneyPlanner {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Segment segment = context.mock(Segment.class);
	private final Trip trip = context.mock(Trip.class);
	private final RoadNetwork roadNetwork = context.mock(RoadNetwork.class);

	private final Junction origin = context.mock(Junction.class, "origin");
	private final Junction destination = context.mock(Junction.class, "destination");

	@Test
	public void itineraryBetweenAdjacentJunctionsContainsOnlyTheSegmentBetweenOriginAndDestinationJunctions()
			throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(trip).origin(); will(returnValue(origin));
				oneOf(trip).destination(); will(returnValue(destination));
				oneOf(roadNetwork).route(origin, destination); will(returnList(segment));
			}
		});

		assertThat(planItineraryForTrip(trip, roadNetwork),
				itineraryRouteIs(segment));
	}
}
