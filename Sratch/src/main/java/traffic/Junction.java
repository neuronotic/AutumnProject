package traffic;


public interface Junction extends Cell {

	String name();

	void addVehicle(Vehicle vehicle);

	void step();

	void addIncomingLinks(Link link);
	void addOutgoingLink(Link link);

	int inBoundLinksOccupancy();
	int inBoundLinksCapacity();
	int occupancy();
	int capacity();
	Object vehiclesWaitingToJoin();
}