package traffic;

import static java.util.Arrays.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestFlowGroupBuilderImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final FlowGroupFactory flowGroupFactory = context.mock(FlowGroupFactory.class);
	private final TemporalPattern temporalPattern = context.mock(TemporalPattern.class);
	private final FlowBuilder flowBuilder0 = context.mock(FlowBuilder.class, "flowBuilder0");
	private final FlowBuilder flowBuilder1 = context.mock(FlowBuilder.class, "flowBuilder1");
	private final Flow flow0 = context.mock(Flow.class, "flow0");
	private final Flow flow1 = context.mock(Flow.class, "flow1");
	private final FlowGroup flowGroup = context.mock(FlowGroup.class);

	FlowGroupBuilder flowGroupBuilder = new FlowGroupBuilderImpl(flowGroupFactory);

	@Test
	public void makeCreatesFlowGroupWithTemporalPatternAndAllFlows() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(flowBuilder0).make(); will(returnValue(flow0));
				oneOf(flowBuilder1).make(); will(returnValue(flow1));
				oneOf(flowGroupFactory).create(temporalPattern, asList(flow0, flow1)); will(returnValue(flowGroup));
			}
		});
		flowGroupBuilder
			.withTemporalPattern(temporalPattern)
			.withFlow(flowBuilder0)
			.withFlow(flowBuilder1);
		assertThat(flowGroupBuilder.make(), is(flowGroup));
	}

	@Test
	public void withFlowReturnsInstanceItBelongsTo() throws Exception {
		assertThat(flowGroupBuilder.withFlow(null), is(flowGroupBuilder));
	}

	@Test
	public void withTemporalPatternReturnsInstanceItBelongsTo() throws Exception {
		assertThat(flowGroupBuilder.withTemporalPattern(null), is(flowGroupBuilder));
	}
}
