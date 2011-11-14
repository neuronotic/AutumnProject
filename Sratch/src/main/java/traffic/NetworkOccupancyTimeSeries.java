package traffic;

import java.util.List;

public interface NetworkOccupancyTimeSeries {

	void addStepData(NetworkOccupancy currentNetworkOccupancy);

	List<NetworkOccupancy> networkOccupancies();

}
