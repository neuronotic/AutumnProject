package traffic;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class JourneyHistoryImpl implements JourneyHistory {

	private final Vehicle vehicle;
	private final SimulationTime startTime;
	private final SimulationTime endTime;
	private final List<CellTime> cellEntryTimes;

	@Inject JourneyHistoryImpl(
			@Assisted final Vehicle vehicle,
			@Assisted("startTime") final SimulationTime startTime,
			@Assisted final List<CellTime> cellEntryTimes,
			@Assisted("endTime") final SimulationTime endTime) {
				this.vehicle = vehicle;
				this.startTime = startTime;
				this.cellEntryTimes = cellEntryTimes;
				this.endTime = endTime;

	}

	@Override
	public String toString() {
		return String.format("Journey history for %s, with startTime %s and finishTime %s", vehicle.name(), startTime, endTime);
	}
}
