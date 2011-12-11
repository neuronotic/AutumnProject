package traffic.endtoend;

import static java.util.Arrays.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.SimulationTime.*;

import org.junit.Rule;
import org.junit.Test;

import traffic.Cell;
import traffic.ConstantTemporalPattern;
import traffic.FlowBuilder;
import traffic.FlowGroupBuilder;
import traffic.Junction;
import traffic.JunctionBuilder;
import traffic.JunctionOccupancyBuilder;
import traffic.Link;
import traffic.LinkBuilder;
import traffic.LinkOccupancy;
import traffic.LinkOccupancyFactory;
import traffic.Network;
import traffic.NetworkBuilder;
import traffic.NetworkOccupancy;
import traffic.NetworkOccupancyBuilder;
import traffic.Occupancy;
import traffic.OccupancyFactory;
import traffic.Simulation;
import traffic.SimulationBuilder;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class TestNetworkMeasures {
	@Rule public MyGuiceBerryRule guiceBerry = new MyGuiceBerryRule();

	@Inject private LinkOccupancyFactory linkOccupancyFactory;
	@Inject private OccupancyFactory occupancyFactory;
	@Inject private Provider<JunctionBuilder> junctionBuilderProvider;
	@Inject private Provider<SimulationBuilder> simulationBuilderProvider;
	@Inject private Provider<LinkBuilder> linkBuilderProvider;
	@Inject private Provider<NetworkBuilder> networkBuilderProvider;
	@Inject private Provider<FlowGroupBuilder> flowGroupBuilderProvider;
	@Inject private Provider<FlowBuilder> flowBuilderProvider;
	@Inject private Provider<NetworkOccupancyBuilder> networkOccupancyBuilderProvider;
	@Inject private Provider<JunctionOccupancyBuilder> junctionOccupancyBuilderProvider;

	private Junction junction0, junction1;
	private Link link0;

	@Test
	public void fluxTimesForLinkAreSixToTen() throws Exception {
		final Simulation sim = simulationWithFlowGroup();
		final Cell cellToRecord = sim.headCellForLink("link0");
		cellToRecord.recordFlux();
		sim.step(7);
		assertThat(sim.statistics().fluxTimes(cellToRecord), is(asList(time(4), time(5), time(6))));
	}

	@Test
	public void OccupancyOnNetworkWithFlowsDoesNotRemainZero() throws Exception {
		final Simulation sim = simulationWithFlowGroup();
		//assertThat(sim, NetworkMatchers.hasNetworkOccupancy(0)); replace equalTo with these style custom matchers
		assertThat(sim.statistics().currentNetworkOccupancy(), equalTo(networkOccupancyWithZeroOccupancy()));
		sim.step(1);
		assertThat(sim.statistics().currentNetworkOccupancy(), not(equalTo(networkOccupancyWithZeroOccupancy())));
	}

	@Test
	public void OccupancyOnEmptyNetworkRemainsZero() throws Exception {
		final Simulation sim = simulationBuilder()
			.withNetwork(createNetwork())
			.make();
		assertThat(sim.statistics().currentNetworkOccupancy(), equalTo(networkOccupancyWithZeroOccupancy()));
		sim.step(1);
		assertThat(sim.statistics().currentNetworkOccupancy(), equalTo(networkOccupancyWithZeroOccupancy()));
	}

	private Simulation simulationWithFlowGroup() {
		final Network network = createNetwork();
		final Simulation sim = simulationBuilder()
			.withNetwork(network)
			.withFlowGroup(flowGroupBuilderProvider.get()
				.withTemporalPattern(new ConstantTemporalPattern(1))
				.withFlow(flowBuilderProvider.get()
					.withRouteSpecifiedByLinkNames(network, "link0")
					.withProbability(1.0))
					)
			.make();
		return sim;
	}

	private NetworkOccupancy networkOccupancyWithZeroOccupancy() {
		return networkOccupancyBuilderProvider.get()
			.withJunctionOccupancy(junctionOccupancyBuilder(junction0)
				.withOccupancy(occupancy(0,1)))
			.withJunctionOccupancy(junctionOccupancyBuilder(junction1)
				.withOccupancy(occupancy(0,1))
				.withIncomingLinkOccupancy(linkOccupancy(link0, 0, link0.cellCount())))
			.make();
	}


	private Occupancy occupancy(final int occupancy, final int capacity) {
		return occupancyFactory.create(occupancy, capacity);
	}

	private JunctionOccupancyBuilder junctionOccupancyBuilder(final Junction junction) {
		return junctionOccupancyBuilderProvider.get()
			.withJunction(junction);
	}

	private LinkOccupancy linkOccupancy(final Link link, final int occupancy, final int capacity) {
		return linkOccupancyFactory.create(link, occupancyFactory.create(occupancy, capacity));
	}

	private SimulationBuilder simulationBuilder() {
		return simulationBuilderProvider.get();
	}

	private Network createNetwork() {
		createJunctions();
		link0 = link()
				.withName("link0")
				.withInJunction(junction0)
				.withOutJunction(junction1)
				.withLength(3)
				.make();
		return networkBuilderProvider.get()
			.withLink(link0)
			.make();
	}

	private LinkBuilder link() {
		return linkBuilderProvider.get();
	}

	private void createJunctions() {
		junction0 = junctionBuilderProvider.get().withName("junction0").make();
		junction1 = junctionBuilderProvider.get().withName("junction1").make();
	}

}
