package traffic;

import static traffic.RoadNetworkToStringStyle.*;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class JourneyHistoryImpl implements JourneyHistory {
	private int journeyTime;

	@Inject public JourneyHistoryImpl(
			@Assisted final Vehicle vehicle,
			@Assisted("startTime") final SimulationTime startTime,
			@Assisted final List<CellTime> cellTimes,
			@Assisted("finishTime") final SimulationTime finishTime) {

	}


	@Override
	public String toString() {
		return roadNetworkReflectionToString(this);
	}
}
