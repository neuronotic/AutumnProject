package traffic;


public interface Statistics {
	void step(RoadNetwork roadNetwork);

	NetworkOccupancy currentNetworkOccupancy();
}
