package traffic.endtoend;

import traffic.Junction;

public interface RoadUser {
	Junction location();

	void startTrip();

	void step();
}
