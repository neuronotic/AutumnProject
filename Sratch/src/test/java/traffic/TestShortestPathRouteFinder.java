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

		final Link link0 = context.mock(Link.class, "link0");
		final Link link1 = context.mock(Link.class, "link1");
		final Link link2 = context.mock(Link.class, "link2");
		final Link link3 = context.mock(Link.class, "link3");

		final Trip trip = context.mock(Trip.class);
		final Network network = context.mock(Network.class, "network");

		context.checking(new Expectations() {
			{
				oneOf(network).junctions(); will(returnList(junction0, junction1, junction2, junction3));
				oneOf(network).links(); will(returnList(link0, link1, link2, link3));

				oneOf(trip).origin(); will(returnValue(junction0));
				oneOf(trip).destination(); will(returnValue(junction3));

				oneOf(link0).inJunction(); will(returnValue(junction0));
				oneOf(link0).outJunction(); will(returnValue(junction1));
				oneOf(link0).length(); will(returnValue(6));
				oneOf(link1).inJunction(); will(returnValue(junction1));
				oneOf(link1).outJunction(); will(returnValue(junction3));
				oneOf(link1).length(); will(returnValue(3));
				oneOf(link2).inJunction(); will(returnValue(junction0));
				oneOf(link2).outJunction(); will(returnValue(junction2));
				oneOf(link2).length(); will(returnValue(2));
				oneOf(link3).inJunction(); will(returnValue(junction2));
				oneOf(link3).outJunction(); will(returnValue(junction1));
				oneOf(link3).length(); will(returnValue(3));

			}
		});

		final RouteFinder routeFinder = new ShortestPathRouteFinder(network);

		assertThat(routeFinder.calculateItinerary(trip), itineraryRouteIs(link2, link3, link1));
	}
}

