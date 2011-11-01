package traffic;

public interface SimulationBuilder {
	public SimulationBuilder withRoadNetwork(final RoadNetwork roadNetwork);
	public SimulationBuilder withVehicle(Vehicle vehicle);
	public Simulation make();
}
