package traffic.endtoend;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Rule;
import org.junit.Test;

import traffic.ConstantTemporalPattern;
import traffic.FlowBuilder;
import traffic.FlowGroupBuilder;
import traffic.ItineraryImpl;
import traffic.Junction;
import traffic.JunctionFactory;
import traffic.JunctionOccupancyBuilder;
import traffic.Link;
import traffic.LinkBuilder;
import traffic.LinkFluxFactory;
import traffic.LinkOccupancy;
import traffic.LinkOccupancyFactory;
import traffic.Network;
import traffic.NetworkBuilder;
import traffic.NetworkFluxBuilder;
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
	@Inject private LinkFluxFactory linkFluxFactory;
	@Inject private Provider<NetworkFluxBuilder> networkFluxBuilderProvider;
	@Inject private Provider<SimulationBuilder> simulationBuilderProvider;
	@Inject private Provider<LinkBuilder> linkBuilderProvider;
	@Inject private Provider<NetworkBuilder> networkBuilderProvider;
	@Inject private Provider<FlowGroupBuilder> flowGroupBuilderProvider;
	@Inject private Provider<FlowBuilder> flowBuilderProvider;
	@Inject private Provider<NetworkOccupancyBuilder> networkOccupancyBuilderProvider;
	@Inject private Provider<JunctionOccupancyBuilder> junctionOccupancyBuilderProvider;

	private final ConstantTemporalPattern constantTemporalPattern = new ConstantTemporalPattern(1.0);

	private Junction junction0, junction1;
	private Link link0;

	//messages are sent. each step an empty builder is (copied?)used. then the messages just modify it.
	//have builder take another builder as constructor?

/*	@Test
	public void fluxOnEmptyNetworkRemains0() throws Exception {
		final Simulation sim = simulationWithFlowGroup();
		assertThat(sim.statistics().currentNetworkFlux(), notNullValue());
		assertThat(sim.statistics().currentNetworkFlux(), equalTo(networkWithZeroFlux()));
		sim.step(10);
		assertThat(sim.statistics().currentNetworkFlux(), equalTo(networkWithZeroFlux()));
	}

	private NetworkFlux networkWithZeroFlux() {
		return networkFluxBuilderProvider.get()
			.withLinkFlux(linkFluxFactory.create(link0, 0))
			.make();
	}*/

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
			.make();
		assertThat(sim.statistics().currentNetworkOccupancy(), equalTo(networkOccupancyWithZeroOccupancy()));
		sim.step(1);
		assertThat(sim.statistics().currentNetworkOccupancy(), equalTo(networkOccupancyWithZeroOccupancy()));
	}

	private Simulation simulationWithFlowGroup() {
		final Simulation sim = simulationBuilder()
			.withFlowGroup(flowGroupBuilderProvider.get()
				.withTemporalPattern(constantTemporalPattern.withModifier(1))
				.withFlow(flowBuilderProvider.get()
					.withItinerary(new ItineraryImpl(link0))
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
