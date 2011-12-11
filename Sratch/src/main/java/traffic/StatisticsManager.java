package traffic;

import java.util.List;

public interface StatisticsManager {
	NetworkOccupancy currentNetworkOccupancy();
	void step();
	NetworkOccupancyTimeSeries networkOccupancy();
	List<JourneyHistory> getEndedJourneyHistories();
	void receiveJourneyHistoryForEndedJourney(JourneyEndedMessage journeyEndedMessage);
	void receiveCellOccupantDepartedMessage(
			CellOccupantDepartedMessage departureMessage);
	List<SimulationTime> fluxTimes(Cell cell);
}
