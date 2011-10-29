package traffic;

public class ItineraryImpl implements Itinerary {
	private final Segment route;

	public ItineraryImpl(final Segment route) {
		this.route = route;
	}

	@Override
	public Segment route() {
		return route;
	}
}
