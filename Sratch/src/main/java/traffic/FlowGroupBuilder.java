package traffic;

public interface FlowGroupBuilder {
	FlowGroup make();

	FlowGroupBuilder withTemporalPattern(TemporalPattern temporalPattern);

	FlowGroupBuilder withFlow(FlowBuilder flowBuilder);


}
