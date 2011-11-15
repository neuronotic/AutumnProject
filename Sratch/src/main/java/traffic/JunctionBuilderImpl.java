package traffic;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class JunctionBuilderImpl implements JunctionBuilder {


	private String name;
	private JunctionControllerStrategyBuilder junctionControllerStrategyBuilder;
	private final JunctionFactory junctionFactory;

	@Inject public JunctionBuilderImpl(
			final JunctionFactory junctionFactory,
			@Named("default") final JunctionControllerStrategyBuilder nullJunctionControllerStrategyBuilder) {
		this.junctionFactory = junctionFactory;
		junctionControllerStrategyBuilder = nullJunctionControllerStrategyBuilder;
	}

	@Override
	public JunctionBuilder withJunctionControllerStrategy(
			final JunctionControllerStrategyBuilder junctionControllerStrategyBuilder) {
		this.junctionControllerStrategyBuilder = junctionControllerStrategyBuilder;
		return this;
	}

	@Override
	public JunctionBuilder withName(final String name) {
		this.name = name;
		return this;
	}

	@Override
	public Junction make() {
		return junctionFactory.createJunction(name);
	}
}
