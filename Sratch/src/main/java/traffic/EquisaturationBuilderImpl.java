package traffic;

import static traffic.SimulationTime.*;

import com.google.inject.Inject;

public class EquisaturationBuilderImpl implements EquisaturationBuilder {

	private SimulationTime period;
	private final EquisaturationFactory equisaturationFactory;
	private SimulationTime switchingDelay =  time(0);

	@Inject public EquisaturationBuilderImpl(final EquisaturationFactory equisaturationFactory) {
		this.equisaturationFactory = equisaturationFactory;
	}

	@Override
	public EquisaturationBuilder withPeriod(final SimulationTime period) {
		this.period = period;
		return this;
	}

	@Override
	public JunctionController make() {
		return equisaturationFactory.create(period, switchingDelay);
	}

	@Override
	public String name() {
		return "Equisaturation";
	}

	@Override
	public EquisaturationBuilder withSwitchingDelay(final SimulationTime switchingDelay) {

		this.switchingDelay = switchingDelay;
		return this;
	}

}
