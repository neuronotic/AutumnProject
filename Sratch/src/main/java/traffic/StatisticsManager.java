package traffic;

public interface StatisticsManager {
	NetworkOccupancy currentNetworkOccupancy();
	NetworkFlux currentNetworkFlux();
	void step();
}
