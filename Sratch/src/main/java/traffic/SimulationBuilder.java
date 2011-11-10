package traffic;

public interface SimulationBuilder {
	public SimulationBuilder withRoadNetwork(final RoadNetwork roadNetwork);
	public Simulation make();
}
