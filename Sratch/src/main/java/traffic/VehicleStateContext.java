package traffic;


public interface VehicleStateContext {

	Cell location();
	SimulationTime journeyTime();

	boolean hasJourneyRemaining();
	void move(Vehicle vehicle);
	void journeyEnded(Vehicle vehicle);
	void subscribeToJourneyEndNotification(Object subscriber);

}
