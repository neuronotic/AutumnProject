package traffic;

import com.google.inject.Inject;

public class PeriodicDutyCycleBuilder implements JunctionControllerStrategyBuilder {

	private SimulationTime period;
	private final PeriodicDutyCycleFactory periodicDutyCycleFactory;

	@Inject PeriodicDutyCycleBuilder(final PeriodicDutyCycleFactory periodicDutyCycleFactory) {
		this.periodicDutyCycleFactory = periodicDutyCycleFactory;
	}

	@Override
	public JunctionControllerStrategy make(final Junction junction) {
		return periodicDutyCycleFactory.create(junction, period);
	}

	@Override
	public JunctionControllerStrategyBuilder withPeriod(final SimulationTime period) {
		this.period = period;
		return this;
	}


}
