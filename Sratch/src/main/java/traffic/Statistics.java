package traffic;


public interface Statistics {
	void step(Network network);

	NetworkOccupancy currentNetworkOccupancy();
}
