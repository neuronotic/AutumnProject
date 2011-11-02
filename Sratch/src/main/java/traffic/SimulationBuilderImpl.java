package traffic;

import com.google.inject.Inject;

public class SimulationBuilderImpl implements SimulationBuilder {


	private RoadNetwork roadNetwork;
	private final VehicleManager vehicleManager;
	private final SimulationFactory simulationFactory;

	@Inject public SimulationBuilderImpl(final SimulationFactory simulationFactory, final VehicleManager vehicleManager) {
		this.simulationFactory = simulationFactory;
		this.vehicleManager = vehicleManager;
	}

	public SimulationBuilder withRoadNetwork(final RoadNetwork roadNetwork) {
		this.roadNetwork = roadNetwork;
		return this;
	}

	@Override
	public Simulation make() {
		return simulationFactory.createSimulation(roadNetwork);
	}

}
