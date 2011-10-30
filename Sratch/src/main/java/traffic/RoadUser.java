package traffic;


public interface RoadUser {
	Cell location();

	void step();

	int journeyTime();
}
