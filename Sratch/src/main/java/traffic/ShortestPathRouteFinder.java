package traffic;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class ShortestPathRouteFinder implements RouteFinder {

	private final RoadNetwork roadNetwork;

	@Inject public ShortestPathRouteFinder(@Assisted final RoadNetwork roadNetwork) {
		this.roadNetwork = roadNetwork;
	}

	@Override
	public Itinerary calculateItinerary(final Trip trip) {
		return new ItineraryImpl(roadNetwork.route(trip));
	}

}
