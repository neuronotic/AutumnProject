package traffic;

import java.util.ArrayList;
import java.util.List;

public class NetworkOccupancyTimeSeriesImpl implements
		NetworkOccupancyTimeSeries {

	private final List<NetworkOccupancy> networkOccupancyTimeSeries = new ArrayList<NetworkOccupancy>();

	@Override
	public void addStepData(final NetworkOccupancy networkOccupancy) {
		networkOccupancyTimeSeries.add(networkOccupancy);
	}

	@Override
	public List<NetworkOccupancy> networkOccupancies() {
		return networkOccupancyTimeSeries;
	}


}
