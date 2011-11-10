package traffic;

import java.util.List;


public interface VehicleManager {
	List<Vehicle> vehicles();

	void step();

	void step(int i);
	List<JourneyHistory> getEndedJourneyHistories();
	void journeyEnded(JourneyEndedMessage journeyEndedMessage);
	void journeyStarted(JourneyStartedMessage journeyStartedMessage);
}
