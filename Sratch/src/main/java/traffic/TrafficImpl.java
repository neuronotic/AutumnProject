package traffic;

import java.util.List;

import org.jfree.ui.RefineryUtilities;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class TrafficImpl implements Traffic {
	private final VehicleManager vehicleManager = null;
	private final SimulationBuilder simulationBuilder;
	private final DefaultNetworks defaultNetworks;

	@Inject private Provider<FlowGroupBuilder> flowGroupBuilderProvider;
	@Inject private Provider<FlowBuilder> flowBuilderProvider;

	private final ConstantTemporalPattern constantTemporalPattern = new ConstantTemporalPattern(1.0);

	@Inject public TrafficImpl(final SimulationBuilder simulationBuilder, final DefaultNetworks defaultNetworks) {
		this.simulationBuilder = simulationBuilder;
		this.defaultNetworks = defaultNetworks;
	}

	@Override
	public void start(final String[] args) {


		final Network network = defaultNetworks.xNetwork4Link();

		final Simulation sim = simulationBuilder
			.withNetwork(network)
			.withFlowGroup(flowGroupBuilderProvider.get()
				.withTemporalPattern(constantTemporalPattern.withModifier(1))
				.withFlow(flowBuilderProvider.get()
						.withItinerary(new ItineraryImpl(network.linkNamed("link0"), network.linkNamed("link1")))
						.withProbability(0.2))
				.withFlow(flowBuilderProvider.get()
						.withItinerary(new ItineraryImpl(network.linkNamed("link2"), network.linkNamed("link3")))
						.withProbability(0.0)) )
			.make();

		sim.step(50);
		final NetworkOccupancyTimeSeries networkOccupancyTimeSeries = sim.statistics().networkOccupancy();
		final List<JourneyHistory> journeyHistories = sim.statistics().getEndedJourneyHistories();

		final MyGraphing graph = new MyGraphing("graphhhh", network, networkOccupancyTimeSeries);
		graph.pack();
		RefineryUtilities.centerFrameOnScreen(graph);
        graph.setVisible(true);

	}

}
