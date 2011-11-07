package traffic;


public interface Cell {
	boolean isOccupied();
	boolean enter(Vehicle vehicle);
	String name();
	void leave(Vehicle vehicle);

}
