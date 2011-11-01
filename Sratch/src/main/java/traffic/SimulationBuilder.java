package traffic;

public interface SimulationBuilder {
	public SimulationBuilder withRoadNetwork(final RoadNetwork roadNetwork);
	public SimulationBuilder withVehicleManager(final VehicleManager vehicleManager);
	public Simulation make();
}
