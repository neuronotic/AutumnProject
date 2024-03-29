package traffic;

import java.util.List;


public interface VehicleManager {
	List<Vehicle> vehicles();

	void step();

	void journeyEnded(JourneyEndedMessage journeyEndedMessage);
	void journeyStarted(JourneyStartedMessage journeyStartedMessage);
}
