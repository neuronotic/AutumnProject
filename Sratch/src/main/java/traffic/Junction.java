package traffic;

import traffic.endtoend.RoadUser;

public interface Junction extends Cell {
	void enter(RoadUser roadUser);

	String name();
}