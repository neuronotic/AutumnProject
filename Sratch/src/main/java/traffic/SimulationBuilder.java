package traffic;

public interface SimulationBuilder {
	public SimulationBuilder withRoadNetwork(final RoadNetwork roadNetwork);
	SimulationBuilder withVehicleManager(VehicleManager vehicleManager);
	public Simulation make();
}
