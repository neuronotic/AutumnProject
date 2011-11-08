package traffic;

public interface TimeKeeper {

	SimulationTime currentTime();

	void step();

}
