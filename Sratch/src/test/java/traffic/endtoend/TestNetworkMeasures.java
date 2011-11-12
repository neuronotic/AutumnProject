package traffic.endtoend;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.SimulationTime.*;

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
import traffic.NetworkOccupancy;
import traffic.NetworkOccupancyBuilder;
import traffic.Network;
import traffic.NetworkBuilder;
import traffic.Segment;
import traffic.SegmentBuilder;
import traffic.SegmentOccupancy;
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

	@Inject private Provider<SimulationBuilder> simulationBuilderProvider;
	@Inject private Provider<VehicleBuilder> vehicleBuilderProvider;
	@Inject private Provider<SegmentBuilder> segmentBuilderProvider;
	@Inject private Provider<NetworkBuilder> networkBuilderProvider;
	@Inject private Provider<JourneyHistoryBuilder> JourneyHistoryBuilderProvider;
	@Inject private Provider<FlowGroupBuilder> flowGroupBuilderProvider;
	@Inject private Provider<FlowBuilder> flowBuilderProvider;
	@Inject private Provider<NetworkOccupancyBuilder> networkOccupancyBuilderProvider;
	@Inject private Provider<JunctionOccupancyBuilder> junctionOccupancyBuilderProvider;

	@Inject DefaultNetworks defaultNetworks;
	private final ConstantTemporalPattern constantTemporalPattern = new ConstantTemporalPattern(1.0);

	private Junction junction0, junction1, junction2, junction3;
	private Segment segment0, segment1, segment2;
	private Network network;
	private JourneyHistory history0, history1;
	private Vehicle vehicle0, vehicle1;

	@Test
	public void congestionOnNetworkWithFlowsDoesNotRemainZero() throws Exception {
		final Network network = defaultNetworks.xNetwork4Segment();

		final Segment segment0 = network.segments().get(0);
		final Segment segment1 = network.segments().get(1);
		final Segment segment2 = network.segments().get(2);
		final Segment segment3 = network.segments().get(3);

		final Simulation sim = simulationBuilderProvider.get()
			.withNetwork(network)
			.withFlowGroup(flowGroupBuilderProvider.get()
				.withTemporalPattern(constantTemporalPattern.withModifier(1))
				.withFlow(flowBuilderProvider.get()
					.withItinerary(new ItineraryImpl(segment0, segment1))
					.withProbability(1.0))
				.withFlow(flowBuilderProvider.get()
					.withItinerary(new ItineraryImpl(segment2, segment3))
					.withProbability(1.0)) )
			.make();

		final NetworkOccupancy expectedInitialNetworkOccupancy = networkOccupancyWithZeroOccupancy();
		assertThat(sim.statistics().currentNetworkOccupancy(), equalTo(expectedInitialNetworkOccupancy));
		sim.step(1);
		assertThat(sim.statistics().currentNetworkOccupancy(), not(equalTo(expectedInitialNetworkOccupancy)));
	}


	@Test
	public void OccupancyOnEmptyNetworkRemainsZero() throws Exception {
		final Network network = defaultNetworks.xNetwork4Segment();
		final Simulation sim = simulationBuilderProvider.get()
			.withNetwork(network)
			.make();

		final NetworkOccupancy expectedInitialNetworkOccupancy = networkOccupancyWithZeroOccupancy();

		assertThat(sim.statistics().currentNetworkOccupancy(), equalTo(expectedInitialNetworkOccupancy));
		sim.step(1);
		assertThat(sim.statistics().currentNetworkOccupancy(), equalTo(expectedInitialNetworkOccupancy));

	}


	private NetworkOccupancy networkOccupancyWithZeroOccupancy() {
		return networkOccupancyBuilderProvider.get()
			.withJunctionOccupancy(junctionOccupancyBuilder(junction0))
			.withJunctionOccupancy(junctionOccupancyBuilder(junction3))
			.withJunctionOccupancy(junctionOccupancyBuilder(junction1)
				.withOccupancy(0)
				.withIncomingSegmentOccupancy(segmentOccupancyWithZeroOccupancy(segment0))
				.withIncomingSegmentOccupancy(segmentOccupancyWithZeroOccupancy(segment2)))
			.withJunctionOccupancy(junctionOccupancyBuilder(junction2)
				.withOccupancy(0)
				.withIncomingSegmentOccupancy(segmentOccupancyWithZeroOccupancy(segment1)))
			.make();
	}


	private JunctionOccupancyBuilder junctionOccupancyBuilder(final Junction junction) {
		return junctionOccupancyBuilderProvider.get()
			.withJunction(junction);
	}

	private SegmentOccupancy segmentOccupancyWithZeroOccupancy(final Segment segment02) {
		// TODO Auto-generated method stub
		return null;

	}

	private SimulationBuilder simulationBuilder() {
		return simulationBuilderProvider.get()
				.withNetwork(network);
	}

	private void createVehicles() {
		vehicle0 = vehicleBuilderProvider.get()
			.withName("vehicle0")
			.withItinerary(new ItineraryImpl(segment0, segment2))
			.make();
		vehicle1 = vehicleBuilderProvider.get()
			.withName("vehicle1")
			.withItinerary(new ItineraryImpl(segment1, segment2))
			.make();
	}

	private void createNetwork() {
		network = networkBuilderProvider.get()
			.withSegment(segment0)
			.withSegment(segment1)
			.withSegment(segment2)
			.make();
	}

	private void createSegments() {
		segment0 = segment()
			.withName("segment0")
			.withInJunction(junction0)
			.withOutJunction(junction1)
			.withLength(1)
			.make();
		segment1 = segment()
			.withName("segment1")
			.withInJunction(junction3)
			.withOutJunction(junction1)
			.withLength(1)
			.make();
		segment2 = segment()
			.withName("segment2")
			.withInJunction(junction1)
			.withOutJunction(junction2)
			.withLength(2)
			.make();
	}

	private SegmentBuilder segment() {
		return segmentBuilderProvider.get();
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
				.withCellEntryTime(segment0.getCell(0),time(1))
				.withCellEntryTime(junction1, time(2))
				.withCellEntryTime(segment2.getCell(0),time(3))
				.withCellEntryTime(segment2.getCell(1),time(4))
				.withCellEntryTime(junction2, time(5))
				.make(vehicle0);

		history1 = JourneyHistoryBuilderProvider.get()
				.withStartTime(time(0))
				.withEndTime(time(7))
				.withCellEntryTime(junction3, time(0))
				.withCellEntryTime(segment1.getCell(0),time(1))
				.withCellEntryTime(junction1, time(3))
				.withCellEntryTime(segment2.getCell(0),time(4))
				.withCellEntryTime(segment2.getCell(1),time(5))
				.withCellEntryTime(junction2, time(6))
				.make(vehicle1);
	}

}
