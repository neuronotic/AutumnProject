package traffic;


public class RoadUserFactoryImpl implements RoadUserFactory {
	@Override
	public RoadUser createRoadUser(final Itinerary itinerary) {
		return new RoadUserImpl(itinerary.iterator(), new JourneyHistoryImpl());
	}
}
