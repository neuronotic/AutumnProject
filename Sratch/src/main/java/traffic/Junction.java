package traffic;


public interface Junction extends Cell {

	String name();

	void addVehicle(Vehicle vehicle);

	void step();

	void addIncomingSegment(Segment segment);
	void addOutgoingSegment(Segment segment);

	int inBoundSegmentsOccupancy();
	int inBoundSegmentsCapacity();
	int occupancy();
	int capacity();
	Object vehiclesWaitingToJoin();
}