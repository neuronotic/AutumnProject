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

	@Test
	public void constructingCallsConverterToBuildGraph() {
		final Junction junction0 = context.mock(Junction.class, "junction0");
		final Junction junction1 = context.mock(Junction.class, "junction1");
		final Junction junction2 = context.mock(Junction.class, "junction2");
		final Junction junction3 = context.mock(Junction.class, "junction3");

		final Segment segment0 = context.mock(Segment.class, "segment0");
		final Segment segment1 = context.mock(Segment.class, "segment1");
		final Segment segment2 = context.mock(Segment.class, "segment2");
		final Segment segment3 = context.mock(Segment.class, "segment3");

		final Trip trip = context.mock(Trip.class);
		final Network network = context.mock(Network.class, "network");

		context.checking(new Expectations() {
			{
				oneOf(network).junctions(); will(returnList(junction0, junction1, junction2, junction3));
				oneOf(network).segments(); will(returnList(segment0, segment1, segment2, segment3));

				oneOf(trip).origin(); will(returnValue(junction0));
				oneOf(trip).destination(); will(returnValue(junction3));

				oneOf(segment0).inJunction(); will(returnValue(junction0));
				oneOf(segment0).outJunction(); will(returnValue(junction1));
				oneOf(segment0).length(); will(returnValue(6));
				oneOf(segment1).inJunction(); will(returnValue(junction1));
				oneOf(segment1).outJunction(); will(returnValue(junction3));
				oneOf(segment1).length(); will(returnValue(3));
				oneOf(segment2).inJunction(); will(returnValue(junction0));
				oneOf(segment2).outJunction(); will(returnValue(junction2));
				oneOf(segment2).length(); will(returnValue(2));
				oneOf(segment3).inJunction(); will(returnValue(junction2));
				oneOf(segment3).outJunction(); will(returnValue(junction1));
				oneOf(segment3).length(); will(returnValue(3));

			}
		});

		final RouteFinder routeFinder = new ShortestPathRouteFinder(network);

		assertThat(routeFinder.calculateItinerary(trip), itineraryRouteIs(segment2, segment3, segment1));
	}
}

