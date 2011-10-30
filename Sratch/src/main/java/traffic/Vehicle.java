package traffic;


public interface Vehicle {
	Cell location();

	void step();

	int journeyTime();
}
