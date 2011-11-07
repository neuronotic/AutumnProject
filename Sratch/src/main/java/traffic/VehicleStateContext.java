package traffic;


public interface VehicleStateContext {

	Cell location();
	int journeyTime();

	boolean hasJourneyRemaining();
	void move(Vehicle vehicle);

}
