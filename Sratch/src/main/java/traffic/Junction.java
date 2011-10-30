package traffic;


public interface Junction extends Cell {
	void enter(Vehicle vehicle);

	String name();
}