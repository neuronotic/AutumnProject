package traffic;

import java.util.List;

import com.google.inject.assistedinject.Assisted;

public interface JourneyHistoryFactory {

	JourneyHistory create(
			Vehicle vehicle,
			@Assisted("startTime") SimulationTime startTime,
			List<CellTime> cellEntryTimes,
			@Assisted("endTime") SimulationTime endTime);

}