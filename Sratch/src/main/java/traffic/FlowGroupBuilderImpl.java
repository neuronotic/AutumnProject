package traffic;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

public class FlowGroupBuilderImpl implements FlowGroupBuilder {

	private final FlowGroupFactory flowGroupFactory;
	private final List<FlowBuilder> flowBuilders = new ArrayList<FlowBuilder>();
	private TemporalPattern temporalPattern;

	@Inject public FlowGroupBuilderImpl(final FlowGroupFactory flowGroupFactory) {
		this.flowGroupFactory = flowGroupFactory;
	}

	@Override
	public FlowGroup make() {
		final List<Flow> flows = new ArrayList<Flow>();
		for (final FlowBuilder builder : flowBuilders) {
			flows.add(builder.make());
		}
		return flowGroupFactory.create(temporalPattern, flows);
	}

	@Override
	public FlowGroupBuilder withTemporalPattern(final TemporalPattern temporalPattern) {
		this.temporalPattern = temporalPattern;
		return this;
	}

	@Override
	public FlowGroupBuilder withFlow(final FlowBuilder flowBuilder) {
		flowBuilders.add(flowBuilder);
		return this;
	}
}
