package traffic;

import static traffic.SimulationTime.*;

import com.google.inject.Inject;

public class DutyCycleBuilderImpl implements DutyCycleBuilder {

	private SimulationTime period;
	private final DutyCycleFactory dutyCycleFactory;
	private SimulationTime switchingDelay =  time(0);

	@Inject public DutyCycleBuilderImpl(
			final DutyCycleFactory dutyCycleFactory) {
				this.dutyCycleFactory = dutyCycleFactory;
	}

	@Override
	public JunctionController make() {
		return dutyCycleFactory.create(period, switchingDelay);
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

	@Override
	public DutyCycleBuilder withSwitchingDelay(final SimulationTime switchingDelay) {
		this.switchingDelay = switchingDelay;
		return this;
	}

}
