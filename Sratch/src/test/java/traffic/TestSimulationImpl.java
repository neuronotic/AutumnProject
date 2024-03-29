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
	private final Network network = context.mock(Network.class);
	private final FlowGroup flowGroup0 = context.mock(FlowGroup.class, "flowGroup0");
	private final FlowGroup flowGroup1 = context.mock(FlowGroup.class, "flowGroup1");
	private final VehicleManager vehicleManager = context.mock(VehicleManager.class);
	private final VehicleCreatorFactory vehicleCreationManagerFactory = context.mock(VehicleCreatorFactory.class);
	private final VehicleCreator vehicleCreator = context.mock(VehicleCreator.class);
	private final StatisticsManagerFactory statisticsManagerFactory = context.mock(StatisticsManagerFactory.class);
	private final StatisticsManager statisticsManager = context.mock(StatisticsManager.class);
	//private final JourneyHistory journeyHistory1 = context.mock(JourneyHistory.class, "journeyHistory1");

	private final Simulation simulation = simulation();

	@Test
	public void headCellForLinkReturnsHeadCellOfNamedLink() throws Exception {
		final Link link = context.mock(Link.class);
		final Cell cell = context.mock(Cell.class);
		context.checking(new Expectations() {
			{
				oneOf(network).linkNamed("a link"); will(returnValue(link));
				oneOf(link).headCell(); will(returnValue(cell));
			}
		});
		assertThat(simulation.headCellForLink("a link"), is(cell));

	}

	@Test
	public void vehicleManagerNetworkAndStatisticsAreSteppedInOrderWithEachSimulationStep() throws Exception {
		final Sequence steppingOrder = context.sequence("steppingOrder");

		context.checking(new Expectations() {
			{
				oneOf(timeKeeper).step();
				oneOf(network).step(); inSequence(steppingOrder);
				oneOf(vehicleCreator).step(); inSequence(steppingOrder);
				oneOf(vehicleManager).step(); inSequence(steppingOrder);
				oneOf(statisticsManager).step(); inSequence(steppingOrder);
			}
		});
		simulation.step();
	}

	@Test
	public void networkReturnsNetworkConstructedWith() throws Exception {
		assertThat(simulation.network(), is(network));
	}

	private Simulation simulation() {
		context.checking(new Expectations() {
			{
				allowing(timeKeeper).currentTime();
				oneOf(statisticsManagerFactory).create(network); will(returnValue(statisticsManager));
				oneOf(vehicleCreationManagerFactory).create(asList(flowGroup0, flowGroup1)); will(returnValue(vehicleCreator));
			}
		});
		return new SimulationImpl(network, asList(flowGroup0, flowGroup1), timeKeeper, statisticsManagerFactory, vehicleManager, vehicleCreationManagerFactory);
	}

}

