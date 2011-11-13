package traffic.endtoend;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Rule;
import org.junit.Test;

import traffic.ConstantTemporalPattern;
import traffic.DefaultNetworks;
import traffic.FlowBuilder;
import traffic.FlowGroupBuilder;
import traffic.ItineraryImpl;
import traffic.Junction;
import traffic.JunctionFactory;
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
import traffic.TrafficModule;

import com.google.guiceberry.GuiceBerryModule;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class TestNetworkMeasures {

	public static class TrafficTestModule extends AbstractModule {
		@Override
		protected void configure() {
			install(new GuiceBerryModule());
			install(new TrafficModule());
		}
	};
	@Rule public MyGuiceBerryRule guiceBerry = new MyGuiceBerryRule();

	@Inject private JunctionFactory junctionFactory;
	@Inject private LinkOccupancyFactory linkOccupancyFactory;
	@Inject private OccupancyFactory occupancyFactory;

	@Inject private Provider<SimulationBuilder> simulationBuilderProvider;
	@Inject private Provider<LinkBuilder> linkBuilderProvider;
	@Inject private Provider<NetworkBuilder> networkBuilderProvider;
	@Inject private Provider<FlowGroupBuilder> flowGroupBuilderProvider;
	@Inject private Provider<FlowBuilder> flowBuilderProvider;
	@Inject private Provider<NetworkOccupancyBuilder> networkOccupancyBuilderProvider;
	@Inject private Provider<JunctionOccupancyBuilder> junctionOccupancyBuilderProvider;

	@Inject DefaultNetworks defaultNetworks;
	private final ConstantTemporalPattern constantTemporalPattern = new ConstantTemporalPattern(1.0);

	private Junction junction0, junction1;
	private Link link0;
	private Network network;

	@Test
	public void congestionOnNetworkWithFlowsDoesNotRemainZero() throws Exception {
		final Simulation sim = simulationBuilder()
			.withFlowGroup(flowGroupBuilderProvider.get()
				.withTemporalPattern(constantTemporalPattern.withModifier(1))
				.withFlow(flowBuilderProvider.get()
					.withItinerary(new ItineraryImpl(link0))
					.withProbability(1.0))
					)
			.make();
		final NetworkOccupancy expectedInitialNetworkOccupancy = networkOccupancyWithZeroOccupancy();
		assertThat(sim.statistics().currentNetworkOccupancy(), equalTo(expectedInitialNetworkOccupancy));
		sim.step(1);
		assertThat(sim.statistics().currentNetworkOccupancy(), not(equalTo(expectedInitialNetworkOccupancy)));
	}

	@Test
	public void OccupancyOnEmptyNetworkRemainsZero() throws Exception {
		final Simulation sim = simulationBuilder()
			.make();

		final NetworkOccupancy expectedInitialNetworkOccupancy = networkOccupancyWithZeroOccupancy();

		assertThat(sim.statistics().currentNetworkOccupancy(), equalTo(expectedInitialNetworkOccupancy));
		sim.step(1);
		assertThat(sim.statistics().currentNetworkOccupancy(), equalTo(expectedInitialNetworkOccupancy));
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
		return simulationBuilderProvider.get()
				.withNetwork(createNetwork());
	}

	private Network createNetwork() {
		createJunctions();
		createLinks();
		return networkBuilderProvider.get()
			.withLink(link0)
			.make();
	}

	private void createLinks() {
		link0 = link()
			.withName("link0")
			.withInJunction(junction0)
			.withOutJunction(junction1)
			.withLength(3)
			.make();
	}

	private LinkBuilder link() {
		return linkBuilderProvider.get();
	}

	private void createJunctions() {
		junction0 = junctionFactory.createJunction("junction0");
		junction1 = junctionFactory.createJunction("junction1");
	}

}
