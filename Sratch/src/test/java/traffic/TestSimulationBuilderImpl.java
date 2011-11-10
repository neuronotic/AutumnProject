package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestSimulationBuilderImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final SimulationFactory simulationFactory = context.mock(SimulationFactory.class);
	private final RoadNetwork roadNetwork = context.mock(RoadNetwork.class);
	private final Simulation simulation = context.mock(Simulation.class);

	@Test
	public void simulationFactoryCalledToBuildSimulation() throws Exception {

		context.checking(new Expectations() {
			{
				oneOf(simulationFactory).createSimulation(roadNetwork); will(returnValue(simulation));
			}
		});

		final Simulation simulation = new SimulationBuilderImpl(simulationFactory)
			.withRoadNetwork(roadNetwork)
			.make();

		assertThat(simulation, notNullValue());
	}
}
