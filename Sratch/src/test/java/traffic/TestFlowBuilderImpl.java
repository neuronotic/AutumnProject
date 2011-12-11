package traffic;

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

	private final double probability = 0.2;
	private final FlowBuilder flowBuilder = new FlowBuilderImpl(flowFactory);



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
