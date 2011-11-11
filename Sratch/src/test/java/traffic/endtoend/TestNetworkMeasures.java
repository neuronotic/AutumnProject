package traffic.endtoend;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Rule;
import org.junit.Test;

import traffic.DefaultRoadNetworks;
import traffic.Junction;
import traffic.RoadNetwork;
import traffic.Simulation;
import traffic.SimulationBuilder;
import traffic.Statistics;
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

	@Inject private Provider<SimulationBuilder> simulationBuilderProvider;
	@Inject DefaultRoadNetworks defaultRoadNetworks;

	@Test
	public void congestionOnEmptyNetworkIsOAtEveryJunction() throws Exception {
		final RoadNetwork roadNetwork = defaultRoadNetworks.xNetwork4Segment();
		final Simulation sim = simulationBuilderProvider.get()
			.withRoadNetwork(roadNetwork)
			.make();

		final Statistics stats = sim.getStatistics();
		assertThat(stats.congestion(), equalTo(0.0));
		for (final Junction junction : roadNetwork.junctions()) {
			assertThat(stats.congestion(junction), equalTo(0.0));
		}
	}
}
