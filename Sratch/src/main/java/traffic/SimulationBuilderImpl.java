package traffic;

import com.google.inject.Inject;

public class SimulationBuilderImpl implements SimulationBuilder {


	private RoadNetwork roadNetwork;
	private VehicleManager vehicleManager;
	private final SimulationFactory simulationFactory;

	@Inject public SimulationBuilderImpl(final SimulationFactory simulationFactory) {
		this.simulationFactory = simulationFactory;
	}

	@Override
	public SimulationBuilder withRoadNetwork(final RoadNetwork roadNetwork) {
		this.roadNetwork = roadNetwork;
		return this;
	}

	@Override
	public Simulation make() {
		return simulationFactory.createSimulation(roadNetwork);
	}

	@Override
	public SimulationBuilder withFlowGroup(final FlowGroupBuilder withFlow) {
		// TODO Auto-generated method stub
		return null;

	}

}
