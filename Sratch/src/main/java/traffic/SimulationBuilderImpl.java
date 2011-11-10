package traffic;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

public class SimulationBuilderImpl implements SimulationBuilder {


	private RoadNetwork roadNetwork;
	private final List<FlowGroupBuilder> flowGroupBuilders = new ArrayList<FlowGroupBuilder>();

	private final SimulationFactory simulationFactory;

	@Inject public SimulationBuilderImpl(final SimulationFactory simulationFactory) {
		this.simulationFactory = simulationFactory;
	}

	@Override
	public Simulation make() {
		final List<FlowGroup> flowGroups = new ArrayList<FlowGroup>();
		for (final FlowGroupBuilder builder : flowGroupBuilders) {
			flowGroups.add(builder.make());
		}
		return simulationFactory.createSimulation(roadNetwork, flowGroups);
	}

	@Override
	public SimulationBuilder withRoadNetwork(final RoadNetwork roadNetwork) {
		this.roadNetwork = roadNetwork;
		return this;
	}

	@Override
	public SimulationBuilder withFlowGroup(final FlowGroupBuilder flowGroupBuilder) {
		flowGroupBuilders.add(flowGroupBuilder);
		return this;
	}

}
