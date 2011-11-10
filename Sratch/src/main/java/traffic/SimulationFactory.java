package traffic;

public interface SimulationFactory {
	Simulation createSimulation(RoadNetwork roadNetwork);
}
