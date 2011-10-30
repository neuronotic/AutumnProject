package traffic;


public class JourneyPlanner {
	public static Itinerary planItineraryForTrip(final Trip trip, final RoadNetwork roadNetwork) {
		return new ItineraryImpl(roadNetwork.route(trip.origin(), trip.destination()));
	}
}
