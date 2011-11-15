package traffic;

public interface JunctionBuilder {
	JunctionBuilder withJunctionControllerStrategy(
			JunctionControllerStrategyBuilder junctionControllerStrategyBuilder);
	JunctionBuilder withName(String name);
	Junction make();

}
