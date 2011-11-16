package traffic;

import com.google.inject.Inject;

public class NullJunctionControllerStrategyBuilder implements JunctionControllerStrategyBuilder {

	private final NullJunctionControllerStrategy nullJunctionControllerStrategy;

	@Inject NullJunctionControllerStrategyBuilder(final NullJunctionControllerStrategy nullJunctionControllerStrategy) {
		this.nullJunctionControllerStrategy = nullJunctionControllerStrategy;
	}

	@Override
	public JunctionControllerStrategy make(final Junction junction) {
		return nullJunctionControllerStrategy;
	}

	@Override
	public JunctionControllerStrategyBuilder withPeriod(final SimulationTime period) {
		// TODO Auto-generated method stub
		return null;

	}

}
