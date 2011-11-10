package traffic;


public interface Junction extends Cell {

	String name();

	void step();
	void addVehicle(Vehicle vehicle);
}