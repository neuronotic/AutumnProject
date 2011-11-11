package traffic;


public interface Junction extends Cell {

	String name();

	void addVehicle(Vehicle vehicle);
}