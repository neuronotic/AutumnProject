package traffic;

import com.google.inject.Inject;

public class EquisaturationBuilder implements JunctionControllerStrategyBuilder {

	private SimulationTime period;
	private final EquisaturationFactory equisaturationFactory;

	@Inject EquisaturationBuilder(final EquisaturationFactory equisaturationFactory) {
		this.equisaturationFactory = equisaturationFactory;
	}

	@Override
	public JunctionControllerStrategy make(final Junction junction) {
		return equisaturationFactory.create(junction, period);
	}

	@Override
	public JunctionControllerStrategyBuilder withPeriod(final SimulationTime period) {
		this.period = period;
		return this;
	}
}
