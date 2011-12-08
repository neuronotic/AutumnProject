package traffic;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class JunctionBuilderImpl implements JunctionBuilder {
	private String name;
	private final JunctionFactory junctionFactory;
	private JunctionController junctionController;
	private JunctionControllerBuilder controllerBuilder;

	@Inject public JunctionBuilderImpl(
			final JunctionFactory junctionFactory,
			@Named("defaultJunctionController") final JunctionController nullJunctionController) {
		this.junctionFactory = junctionFactory;
		junctionController = nullJunctionController;
	}

	@Override
	public Junction make() {
		if (controllerBuilder != null) {
			junctionController = controllerBuilder.make();
		}
		return junctionFactory.createJunction(name, junctionController);
	}

	@Override
	public JunctionBuilder withName(final String name) {
		this.name = name;
		return this;
	}

	@Override
	public JunctionBuilder withControllerBuilder(
			final JunctionControllerBuilder controllerBuilder) {
		this.controllerBuilder = controllerBuilder;
		return this;
	}
}
