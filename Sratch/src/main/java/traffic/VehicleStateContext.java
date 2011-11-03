package traffic;

public interface VehicleStateContext {

	void step();

	Cell location();

	int journeyTime();

}
