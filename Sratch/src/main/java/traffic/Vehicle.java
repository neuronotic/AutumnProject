package traffic;


public interface Vehicle {
	Cell location();

	void step();

	SimulationTime journeyTime();

	String name();

	Flow flow();
}
