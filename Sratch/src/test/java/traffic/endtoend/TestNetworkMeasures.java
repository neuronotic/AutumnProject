package traffic.endtoend;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Rule;
import org.junit.Test;

import traffic.ConstantTemporalPattern;
import traffic.DefaultRoadNetworks;
import traffic.FlowBuilder;
import traffic.FlowGroupBuilder;
import traffic.ItineraryImpl;
import traffic.JourneyHistoryBuilder;
import traffic.RoadNetwork;
import traffic.RoadNetworkBuilder;
import traffic.Segment;
import traffic.SegmentBuilder;
import traffic.Simulation;
import traffic.SimulationBuilder;
import traffic.TrafficModule;
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

	@Inject private Provider<SimulationBuilder> simulationBuilderProvider;
	@Inject private Provider<VehicleBuilder> vehicleBuilderProvider;
	@Inject private Provider<SegmentBuilder> segmentBuilderProvider;
	@Inject private Provider<RoadNetworkBuilder> roadNetworkBuilderProvider;
	@Inject private Provider<JourneyHistoryBuilder> JourneyHistoryBuilderProvider;
	@Inject private Provider<FlowGroupBuilder> flowGroupBuilderProvider;
	@Inject private Provider<FlowBuilder> flowBuilderProvider;

	@Inject DefaultRoadNetworks defaultRoadNetworks;
	private final ConstantTemporalPattern constantTemporalPattern = new ConstantTemporalPattern(1.0);

	@Test
	public void congestionOnNonEmptyNetworkIsNot0() throws Exception {
		final RoadNetwork roadNetwork = defaultRoadNetworks.xNetwork4Segment();

		final Segment segment0 = roadNetwork.segments().get(0);
		final Segment segment1 = roadNetwork.segments().get(1);
		final Segment segment2 = roadNetwork.segments().get(2);
		final Segment segment3 = roadNetwork.segments().get(3);

		final Simulation sim = simulationBuilderProvider.get()
			.withRoadNetwork(roadNetwork)
			.withFlowGroup(flowGroupBuilderProvider.get()
				.withTemporalPattern(constantTemporalPattern.withModifier(1))
				.withFlow(flowBuilderProvider.get()
					.withItinerary(new ItineraryImpl(segment0, segment1))
					.withProbability(1.0))
				.withFlow(flowBuilderProvider.get()
					.withItinerary(new ItineraryImpl(segment2, segment3))
					.withProbability(1.0)) )
			.make();

		sim.step(10);
		final int x = 5;
		assertThat(x, equalTo(4));
//		final Statistics stats = sim.statistics();
//		for (final Junction junction : roadNetwork.junctions()) {
//			assertThat(stats.congestion(junction).toList(), equalTo(asList(0.0, 0.0)));
//			assertThat(stats.vehiclesWaiting(junction).toList(), equalTo(asList(0.0, 0.0)));
//		}


	}


	@Test
	public void OccupancyOnEmptyNetworkIsOAtEveryJunction() throws Exception {
		final RoadNetwork roadNetwork = defaultRoadNetworks.xNetwork4Segment();
		final Simulation sim = simulationBuilderProvider.get()
			.withRoadNetwork(roadNetwork)
			.make();
		sim.step(2);

//		final Statistics stats = sim.statistics();
//
//		for (final Junction junction : roadNetwork.junctions()) {
//			assertThat(stats.congestion(junction).toList(), equalTo(asList(0.0, 0.0)));
//			assertThat(stats.vehiclesWaiting(junction).toList(), equalTo(asList(0.0, 0.0)));
//		}
	}
}
