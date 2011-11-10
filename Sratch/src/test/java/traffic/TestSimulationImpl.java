package traffic;

import static java.util.Arrays.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.jmock.Sequence;
import org.junit.Rule;
import org.junit.Test;

public class TestSimulationImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final RoadNetwork roadNetwork = context.mock(RoadNetwork.class);
	private final FlowGroup flowGroup0 = context.mock(FlowGroup.class);
	private final VehicleManager vehicleManager = context.mock(VehicleManager.class);
	private final Junction junction0 = context.mock(Junction.class);
	private final JourneyHistory journeyHistory0 = context.mock(JourneyHistory.class, "journeyHistory0");
	//private final JourneyHistory journeyHistory1 = context.mock(JourneyHistory.class, "journeyHistory1");


	private final Simulation simulation = new SimulationImpl(roadNetwork, asList(flowGroup0), vehicleManager);

	//TODO: test for step being called multiple times?

	@Test
	public void getEndedJourneyHistoriesDelegatesToVehicleManager() throws Exception {
		//TODO: maybe split up functionality eventually so that something else receives the history, while VM just removes vehicles as a result of message?
		context.checking(new Expectations() {
			{
				oneOf(vehicleManager).getEndedJourneyHistories(); will(returnValue(asList(journeyHistory0)));
			}
		});
		assertThat(simulation.getEndedJourneyHistories(), contains(journeyHistory0));
	}

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
