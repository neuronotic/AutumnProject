package traffic;


public interface RoadUserManager {
	RoadUser roadUser(Itinerary itinerary);

	void step();
}
