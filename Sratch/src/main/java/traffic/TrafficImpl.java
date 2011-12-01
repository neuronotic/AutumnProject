package traffic;

import static traffic.SimulationTime.*;
import traffic.graphing.MyGraphing;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class TrafficImpl implements Traffic {
	private final SimulationBuilder simulationBuilder;
	private final DefaultNetworks defaultNetworks;

	@Inject TimeKeeper timeKeeper;
	@Inject private Provider<FlowGroupBuilder> flowGroupBuilderProvider;
	@Inject private Provider<FlowBuilder> flowBuilderProvider;
	@Inject private Provider<EquisaturationBuilder> equisaturationBuilderProvider;
	@Inject private Provider<DutyCycleBuilder> periodicDutyCycleBuilderProvider;

	@Inject public TrafficImpl(final SimulationBuilder simulationBuilder, final DefaultNetworks defaultNetworks) {
		this.simulationBuilder = simulationBuilder;
		this.defaultNetworks = defaultNetworks;
	}

	@Override
	public void start(final String[] args) {
		final double flow1probability = 0.40;
		final double flow2probability = 0.15;

		JunctionControllerBuilder controllerBuilder = null;
		//controllerBuilder = equisaturationBuilderProvider.get().withPeriod(time(5));
		controllerBuilder = periodicDutyCycleBuilderProvider.get().withPeriod(time(5));

		//final Network network = defaultNetworks.yNetwork3Link(junctionController);
		final Network network = defaultNetworks.vNetwork2Link(controllerBuilder);

		final Simulation sim = simulationBuilder
			.withNetwork(network)
			.withFlowGroup(flowGroupBuilderProvider.get()
				.withTemporalPattern(new ConstantTemporalPattern(1))
				.withFlow(flowBuilderProvider.get()
						.withItinerary(new ItineraryImpl(network.linkNamed("link0")))//, network.linkNamed("link1")))
						.withProbability(flow1probability))
				.withFlow(flowBuilderProvider.get()
						.withItinerary(new ItineraryImpl(network.linkNamed("link1")))//, network.linkNamed("link3")))
						.withProbability(flow2probability)) )

			.make();

		sim.step(1500);

		new MyGraphing("congestion", network, sim.statistics().networkOccupancy(), controllerBuilder.name(), flow1probability, flow2probability);
		new MyGraphing("jounrey times", sim.statistics().getEndedJourneyHistories(), controllerBuilder.name(), flow1probability, flow2probability);
	}

}
