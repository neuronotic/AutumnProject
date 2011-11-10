package traffic;

import static java.util.Arrays.*;

import org.jmock.Expectations;
import org.jmock.Sequence;
import org.junit.Rule;
import org.junit.Test;

public class TestSimulationImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final RoadNetwork roadNetwork = context.mock(RoadNetwork.class);
	private final VehicleManager vehicleManager = context.mock(VehicleManager.class);
	private final Junction junction0 = context.mock(Junction.class);
	private final Simulation simulation = new SimulationImpl(roadNetwork, vehicleManager);

	//TODO: test for step being called multiple times?

	@Test
	public void VehicleManagerAndJunctionsAreSteppedInOrderWithEachSimulationStep() throws Exception {
		final Sequence steppingOrder = context.sequence("steppingOrder");

		context.checking(new Expectations() {
			{
				oneOf(roadNetwork).junctions(); will(returnValue(asList(junction0)));
				oneOf(junction0).step(); inSequence(steppingOrder);
				oneOf(vehicleManager).step(); inSequence(steppingOrder);
			}
		});
		simulation.step();
	}

}
