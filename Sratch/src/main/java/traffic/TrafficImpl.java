package traffic;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class TrafficImpl implements Traffic {
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
				.withTemporalPattern(new ConstantTemporalPattern(1))
				.withFlow(flowBuilderProvider.get()
						.withItinerary(new ItineraryImpl(network.linkNamed("link0"), network.linkNamed("link1")))
						.withProbability(0.80))
				.withFlow(flowBuilderProvider.get()
						.withItinerary(new ItineraryImpl(network.linkNamed("link2"), network.linkNamed("link1")))
						.withProbability(0.05)) )
			.make();

		sim.step(200);

		new MyGraphing("congestion", network, sim.statistics().networkOccupancy());
		new MyGraphing("jounrey times", sim.statistics().getEndedJourneyHistories());

	}

}
