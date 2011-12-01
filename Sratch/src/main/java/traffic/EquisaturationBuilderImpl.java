package traffic;

import com.google.inject.Inject;

public class EquisaturationBuilderImpl implements EquisaturationBuilder {

	private SimulationTime period;
	private final EquisaturationFactory equisaturationFactory;

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
		return equisaturationFactory.create(period);
	}

	@Override
	public String name() {
		return "Equisaturation";
	}

}
