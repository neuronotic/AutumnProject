package traffic;


public interface Simulation {

	void step(int timesteps);
	void step();
	StatisticsManager statistics();
	SimulationTime time();
	Cell headCellForLink(String linkName);
}
