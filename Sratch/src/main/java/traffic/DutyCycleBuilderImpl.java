package traffic;

import com.google.inject.Inject;

public class DutyCycleBuilderImpl implements DutyCycleBuilder {

	private SimulationTime period;
	private final DutyCycleFactory dutyCycleFactory;

	@Inject public DutyCycleBuilderImpl(
			final DutyCycleFactory dutyCycleFactory) {
				this.dutyCycleFactory = dutyCycleFactory;
	}

	@Override
	public JunctionController make() {
		return dutyCycleFactory.create(period);
	}

	@Override
	public DutyCycleBuilder withPeriod(final SimulationTime period) {
		this.period = period;
		return this;
	}

	@Override
	public String name() {
		return "DutyCycle";
	}

}
