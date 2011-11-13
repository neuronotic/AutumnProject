package traffic;



public interface StatisticsManager {
	void step(Network network);
	NetworkOccupancy currentNetworkOccupancy();
	NetworkFlux currentNetworkFlux();
}
