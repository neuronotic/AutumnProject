package traffic;

public interface SimulationBuilder {
	public Simulation make();
	public SimulationBuilder withNetwork(final Network network);
	public SimulationBuilder withFlowGroup(FlowGroupBuilder flowGroupBuilder);
}
