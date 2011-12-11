package traffic;

import static java.util.Arrays.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestFlowBuilderImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Flow flow = context.mock(Flow.class);
	private final Itinerary itinerary = context.mock(Itinerary.class);
	private final FlowFactory flowFactory = context.mock(FlowFactory.class);
	private final ItineraryFactory itineraryFactory = context.mock(ItineraryFactory.class);
	private final Network network = context.mock(Network.class);
	private final Link link0 = context.mock(Link.class, "link0");
	private final Link link1 = context.mock(Link.class, "link1");
	private final double probability = 0.2;
	private final FlowBuilder flowBuilder = new FlowBuilderImpl(flowFactory, itineraryFactory);

	@Test
	public void withRouteSpecifiedByLinkNamesConstructsItineraryAlongLinks() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(network).linkNamed("link0"); will(returnValue(link0));
				oneOf(network).linkNamed("link1"); will(returnValue(link1));
				oneOf(itineraryFactory).create(asList(link0, link1)); will(returnValue(itinerary));
			}
		});
		assertThat(flowBuilder.withRouteSpecifiedByLinkNames(network, "link0", "link1").itinerary(), is(itinerary));
	}

	@Test
	public void makeCreatesFlowWithItineraryAndProbability() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(flowFactory).create(itinerary, probability); will(returnValue(flow));
			}
		});
		flowBuilder
			.withItinerary(itinerary)
			.withProbability(probability);
		assertThat(flowBuilder.make(), is(flow));
	}

	@Test
	public void withProbabilityReturnsInstanceItBelongsTo() throws Exception {
		assertThat(flowBuilder.withProbability(probability), is(flowBuilder));
	}

	@Test
	public void withItineraryReturnsInstanceItBelongsTo() throws Exception {
		assertThat(flowBuilder.withItinerary(null), is(flowBuilder));
	}
}
