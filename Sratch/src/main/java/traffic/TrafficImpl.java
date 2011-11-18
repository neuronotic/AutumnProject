package traffic;

import traffic.graphing.MyGraphing;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class TrafficImpl implements Traffic {
	private final SimulationBuilder simulationBuilder;
	private final DefaultNetworks defaultNetworks;

	@Inject TimeKeeper timeKeeper;
	@Inject private Provider<FlowGroupBuilder> flowGroupBuilderProvider;
	@Inject private Provider<FlowBuilder> flowBuilderProvider;

	@Inject public TrafficImpl(final SimulationBuilder simulationBuilder, final DefaultNetworks defaultNetworks) {
		this.simulationBuilder = simulationBuilder;
		this.defaultNetworks = defaultNetworks;
	}

	@Override
	public void start(final String[] args) {

		final Network network = defaultNetworks.xNetwork4Link(new JunctionControllerImpl(timeKeeper, new PeriodicDutyCycleStrategy(), SimulationTime.time(5)));

		final Simulation sim = simulationBuilder
			.withNetwork(network)
			.withFlowGroup(flowGroupBuilderProvider.get()
				.withTemporalPattern(new ConstantTemporalPattern(1))
				.withFlow(flowBuilderProvider.get()
						.withItinerary(new ItineraryImpl(network.linkNamed("link0"), network.linkNamed("link1")))
						.withProbability(0.30))
				.withFlow(flowBuilderProvider.get()
						.withItinerary(new ItineraryImpl(network.linkNamed("link2"), network.linkNamed("link1")))
						.withProbability(0.2)) )
			.make();

		sim.step(200);

		new MyGraphing("congestion", network, sim.statistics().networkOccupancy());
		new MyGraphing("jounrey times", sim.statistics().getEndedJourneyHistories());
	}

}
