package traffic;


public interface Junction extends Cell {

	String name();

	void addVehicle(Vehicle vehicle);

	void step();

	void addIncomingLink(Link link);
	void addOutgoingLink(Link link);

	JunctionOccupancy occupancy();
	Object vehiclesWaitingToJoin();
}