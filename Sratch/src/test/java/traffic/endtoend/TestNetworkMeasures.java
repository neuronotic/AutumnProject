package traffic.endtoend;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.SimulationTime.*;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import traffic.ConstantTemporalPattern;
import traffic.DefaultNetworks;
import traffic.FlowBuilder;
import traffic.FlowGroupBuilder;
import traffic.ItineraryImpl;
import traffic.JourneyHistory;
import traffic.JourneyHistoryBuilder;
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
import traffic.Vehicle;
import traffic.VehicleBuilder;

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
	@Inject private Provider<VehicleBuilder> vehicleBuilderProvider;
	@Inject private Provider<LinkBuilder> linkBuilderProvider;
	@Inject private Provider<NetworkBuilder> networkBuilderProvider;
	@Inject private Provider<JourneyHistoryBuilder> JourneyHistoryBuilderProvider;
	@Inject private Provider<FlowGroupBuilder> flowGroupBuilderProvider;
	@Inject private Provider<FlowBuilder> flowBuilderProvider;
	@Inject private Provider<NetworkOccupancyBuilder> networkOccupancyBuilderProvider;
	@Inject private Provider<JunctionOccupancyBuilder> junctionOccupancyBuilderProvider;

	@Inject DefaultNetworks defaultNetworks;
	private final ConstantTemporalPattern constantTemporalPattern = new ConstantTemporalPattern(1.0);

	private Junction junction0, junction1, junction2, junction3;
	private Link link0, link1, link2;
	private Network network;
	private JourneyHistory history0, history1;
	private Vehicle vehicle0, vehicle1;

	@Test
	@Ignore
	public void congestionOnNetworkWithFlowsDoesNotRemainZero() throws Exception {
		final Simulation sim = simulationBuilderProvider.get()
			.withNetwork(createNetwork())
			.withFlowGroup(flowGroupBuilderProvider.get()
				.withTemporalPattern(constantTemporalPattern.withModifier(1))
				.withFlow(flowBuilderProvider.get()
					.withItinerary(new ItineraryImpl(link0, link1))
					.withProbability(1.0))
				.withFlow(flowBuilderProvider.get()
					.withItinerary(new ItineraryImpl(link2, link1))
					.withProbability(1.0)) )
			.make();
		final NetworkOccupancy expectedInitialNetworkOccupancy = networkOccupancyWithZeroOccupancy();
		assertThat(sim.statistics().currentNetworkOccupancy(), equalTo(expectedInitialNetworkOccupancy));
		sim.step(1);
		assertThat(sim.statistics().currentNetworkOccupancy(), not(equalTo(expectedInitialNetworkOccupancy)));
	}

	@Test
	public void OccupancyOnEmptyNetworkRemainsZero() throws Exception {
		final Simulation sim = simulationBuilderProvider.get()
			.withNetwork(createNetwork())
			.make();

		final NetworkOccupancy expectedInitialNetworkOccupancy = networkOccupancyWithZeroOccupancy();

		assertThat(sim.statistics().currentNetworkOccupancy(), equalTo(expectedInitialNetworkOccupancy));
		sim.step(1);
		assertThat(sim.statistics().currentNetworkOccupancy(), equalTo(expectedInitialNetworkOccupancy));
	}


	private NetworkOccupancy networkOccupancyWithZeroOccupancy() {
		return networkOccupancyBuilderProvider.get()
			.withJunctionOccupancy(junctionOccupancyBuilder(junction0)
				.withOccupancy(occupancy(0,0)))
			.withJunctionOccupancy(junctionOccupancyBuilder(junction3)
				.withOccupancy(occupancy(0,0)))
			.withJunctionOccupancy(junctionOccupancyBuilder(junction1)
				.withOccupancy(occupancy(0,0))
				.withIncomingLinkOccupancy(linkOccupancy(link0, 0, 1))
				.withIncomingLinkOccupancy(linkOccupancy(link2, 0, 1)))
			.withJunctionOccupancy(junctionOccupancyBuilder(junction2)
				.withOccupancy(occupancy(0,0))
				.withIncomingLinkOccupancy(linkOccupancy(link1, 0, 1)))
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
				.withNetwork(network);
	}

	private void createVehicles() {
		vehicle0 = vehicleBuilderProvider.get()
			.withName("vehicle0")
			.withItinerary(new ItineraryImpl(link0, link2))
			.make();
		vehicle1 = vehicleBuilderProvider.get()
			.withName("vehicle1")
			.withItinerary(new ItineraryImpl(link1, link2))
			.make();
	}

	private Network createNetwork() {
		createJunctions();
		createLinks();
		return networkBuilderProvider.get()
			.withLink(link0)
			.withLink(link1)
			.withLink(link2)
			.make();
	}

	private void createLinks() {
		link0 = link()
			.withName("link0")
			.withInJunction(junction0)
			.withOutJunction(junction1)
			.withLength(1)
			.make();
		link1 = link()
			.withName("link1")
			.withInJunction(junction3)
			.withOutJunction(junction1)
			.withLength(1)
			.make();
		link2 = link()
			.withName("link2")
			.withInJunction(junction1)
			.withOutJunction(junction2)
			.withLength(2)
			.make();
	}

	private LinkBuilder link() {
		return linkBuilderProvider.get();
	}

	private void createJunctions() {
		junction0 = junctionFactory.createJunction("junction0");
		junction1 = junctionFactory.createJunction("junction1");
		junction2 = junctionFactory.createJunction("junction2");
		junction3 = junctionFactory.createJunction("junction3");
	}

	private void createJourneyHistories() {
		history0 = JourneyHistoryBuilderProvider.get()
				.withStartTime(time(0))
				.withEndTime(time(6))
				.withCellEntryTime(junction0, time(0))
				.withCellEntryTime(link0.getCell(0),time(1))
				.withCellEntryTime(junction1, time(2))
				.withCellEntryTime(link2.getCell(0),time(3))
				.withCellEntryTime(link2.getCell(1),time(4))
				.withCellEntryTime(junction2, time(5))
				.make(vehicle0);

		history1 = JourneyHistoryBuilderProvider.get()
				.withStartTime(time(0))
				.withEndTime(time(7))
				.withCellEntryTime(junction3, time(0))
				.withCellEntryTime(link1.getCell(0),time(1))
				.withCellEntryTime(junction1, time(3))
				.withCellEntryTime(link2.getCell(0),time(4))
				.withCellEntryTime(link2.getCell(1),time(5))
				.withCellEntryTime(junction2, time(6))
				.make(vehicle1);
	}

}