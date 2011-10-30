package traffic.endtoend;

import traffic.Cell;

public interface RoadUser {
	Cell location();

	void step();

	int journeyTime();
}
