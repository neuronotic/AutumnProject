package traffic;

public interface JunctionControllerStrategyBuilder {
	JunctionControllerStrategy make(Junction junction);
	JunctionControllerStrategyBuilder withPeriod(SimulationTime period);

}
