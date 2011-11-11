package traffic;

import static java.util.Arrays.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.jmock.Sequence;
import org.junit.Rule;
import org.junit.Test;

public class TestSimulationImpl {
	//TODO: test for step being called multiple times?
	//TODO: merge VM and VC
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();
	private final TimeKeeper timeKeeper = context.mock(TimeKeeper.class);
	private final RoadNetwork roadNetwork = context.mock(RoadNetwork.class);
	private final FlowGroup flowGroup0 = context.mock(FlowGroup.class, "flowGroup0");
	private final FlowGroup flowGroup1 = context.mock(FlowGroup.class, "flowGroup1");
	private final VehicleManager vehicleManager = context.mock(VehicleManager.class);
	private final VehicleCreatorFactory vehicleCreationManagerFactory = context.mock(VehicleCreatorFactory.class);
	private final Junction junction0 = context.mock(Junction.class);
	private final JourneyHistory journeyHistory0 = context.mock(JourneyHistory.class, "journeyHistory0");
	private final VehicleCreator vehicleCreator = context.mock(VehicleCreator.class);
	//private final JourneyHistory journeyHistory1 = context.mock(JourneyHistory.class, "journeyHistory1");

	private final Simulation simulation = simulation();

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
				oneOf(timeKeeper).step();
				oneOf(roadNetwork).junctions(); will(returnValue(asList(junction0)));
				oneOf(vehicleCreator).step(); inSequence(steppingOrder);
				oneOf(junction0).step(); inSequence(steppingOrder);
				oneOf(vehicleManager).step(); inSequence(steppingOrder);
			}
		});
		simulation.step();
	}

	private SimulationImpl simulation() {
		context.checking(new Expectations() {
			{
				oneOf(vehicleCreationManagerFactory).create(asList(flowGroup0, flowGroup1)); will(returnValue(vehicleCreator));
			}
		});
		return new SimulationImpl(roadNetwork, asList(flowGroup0, flowGroup1), timeKeeper, vehicleManager, vehicleCreationManagerFactory);
	}

}

