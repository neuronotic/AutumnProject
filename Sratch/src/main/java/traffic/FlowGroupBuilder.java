package traffic;

public interface FlowGroupBuilder {

	FlowGroupBuilder withTemporalPattern(TemporalPattern temporalPattern);

	FlowGroupBuilder withFlow(FlowBuilder flowBuilder);

}
