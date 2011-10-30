package traffic;


public interface Junction extends Cell {
	void enter(RoadUser roadUser);

	String name();
}