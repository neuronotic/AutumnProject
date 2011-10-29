package traffic.endtoend;

public class RoadUserFactory {
	public static RoadUser roadUser(final Itinerary itinerary) {
		return new RoadUserImpl(itinerary);
	}
}
