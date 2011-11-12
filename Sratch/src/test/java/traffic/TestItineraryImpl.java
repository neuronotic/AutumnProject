package traffic;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static traffic.ItineraryMatchers.*;
import static traffic.MyJMockActions.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestItineraryImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Link link0 = context.mock(Link.class, "link0");
	private final Link link1 = context.mock(Link.class, "link1");

	private final Junction junctionCell0 = context.mock(Junction.class, "junctionCell0");
	private final Junction junctionCell1 = context.mock(Junction.class, "junctionCell1");
	private final Junction junctionCell2 = context.mock(Junction.class, "junctionCell2");

	private final Cell linkCell0 = context.mock(Cell.class, "linkCell0");
	private final Cell linkCell1 = context.mock(Cell.class, "linkCell1");
	private final Cell linkCell2 = context.mock(Cell.class, "linkCell2");

	@Test
	public void routeReturnsListOfLinksItineraryWasCreatedWith() throws Exception {
		final Itinerary itinerary = new ItineraryImpl(link0, link1);
		assertThat(itinerary, itineraryRouteIs(link0, link1));
		assertThat(itinerary.route(), contains(link0, link1));
	}


	@Test
	public void itineraryContainsCellsFromRouteJunctionsAndLinksInOrderForMultipleLinks() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(link0).cells(); will(returnList(junctionCell0, linkCell0, linkCell1, junctionCell1));
				oneOf(link1).cells(); will(returnList(junctionCell1, linkCell2, junctionCell2));
			}
		});
		assertThat(new ItineraryImpl(link0, link1).cells(), contains(junctionCell0, linkCell0, linkCell1, junctionCell1, linkCell2, junctionCell2));
	}

	@Test
	public void itineraryContainsCellsFromRouteJunctionsAndLinksInOrder() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(link0).cells(); will(returnList(junctionCell0, linkCell0, linkCell1, junctionCell1));
			}
		});
		assertThat(new ItineraryImpl(link0).cells(), contains(junctionCell0, linkCell0, linkCell1, junctionCell1));
	}
}
