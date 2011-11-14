package traffic;

import java.util.List;

public interface StatisticsManager {
	NetworkOccupancy currentNetworkOccupancy();
	NetworkFlux currentNetworkFlux();
	void step();
	NetworkOccupancyTimeSeries networkOccupancy();
	List<JourneyHistory> getEndedJourneyHistories();
	void receiveJourneyHistoryForEndedJourney(JourneyEndedMessage journeyEndedMessage);
}
