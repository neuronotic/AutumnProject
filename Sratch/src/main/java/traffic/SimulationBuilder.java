package traffic;

public interface SimulationBuilder {
	public Simulation make();
	public SimulationBuilder withRoadNetwork(final RoadNetwork roadNetwork);
	public SimulationBuilder withFlowGroup(FlowGroupBuilder flowGroupBuilder);
}
