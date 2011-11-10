package traffic;

public interface FlowBuilder {

	FlowBuilder withSegments(Segment...segments);

	FlowBuilder withProbability(double probability);

}
