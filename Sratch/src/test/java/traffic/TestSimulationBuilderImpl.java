package traffic;

import static java.util.Arrays.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestSimulationBuilderImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final SimulationFactory simulationFactory = context.mock(SimulationFactory.class);
	private final Network network = context.mock(Network.class);
	private final FlowGroupBuilder flowGroupBuilder0 = context.mock(FlowGroupBuilder.class, "flowGroupBuilder0");
	private final FlowGroupBuilder flowGroupBuilder1 = context.mock(FlowGroupBuilder.class, "flowGroupBuilder1");
	private final FlowGroup flowGroup0 = context.mock(FlowGroup.class, "flowGroup0");
	private final FlowGroup flowGroup1 = context.mock(FlowGroup.class, "flowGroup1");
	private final Simulation simulation = context.mock(Simulation.class);

	private final SimulationBuilder simulationBuilder = new SimulationBuilderImpl(simulationFactory);

	@Test
	public void simulationFactoryCalledToBuildSimulationWithSpecifiedDependencies() throws Exception {

		context.checking(new Expectations() {
			{
				oneOf(flowGroupBuilder0).make(); will(returnValue(flowGroup0));
				oneOf(flowGroupBuilder1).make(); will(returnValue(flowGroup1));
				oneOf(simulationFactory).createSimulation(network, asList(flowGroup0, flowGroup1)); will(returnValue(simulation));
			}
		});

		final Simulation simulation = simulationBuilder
			.withNetwork(network)
			.withFlowGroup(flowGroupBuilder0)
			.withFlowGroup(flowGroupBuilder1)
			.make();

		assertThat(simulation, notNullValue());
	}

	@Test
	public void withNetworkReturnsInstanceItBelongsTo() throws Exception {
		assertThat(simulationBuilder.withNetwork(null), is(simulationBuilder));
	}

	@Test
	public void withFlowGroupReturnsInstanceItBelongsTo() throws Exception {
		assertThat(simulationBuilder.withFlowGroup(null), is(simulationBuilder));
	}
}
