package traffic;


public interface Cell {
	String name();
	boolean enter(Vehicle vehicle);
	void leave();
	boolean isOccupied();
}
