package traffic;

import java.util.List;

public interface SimulationFactory {
	Simulation createSimulation(Network network, List<FlowGroup> flowGroups);
}
