package traffic;

import java.util.List;

public interface SimulationFactory {
	Simulation createSimulation(RoadNetwork roadNetwork, List<FlowGroup> flowGroups);
}
