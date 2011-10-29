package traffic;

import java.util.ArrayList;
import java.util.Iterator;

public class ItineraryImpl implements Itinerary {
	private final Segment route;

	public ItineraryImpl(final Segment route) {
		this.route = route;
	}

	@Override
	public Segment route() {
		return route;
	}

	@Override
	public Iterator<Cell> iterator() {
		final ArrayList<Cell> result = new ArrayList<Cell>();
		result.add(route.inJunction());
		for (final Cell cell : route.cellChain()) {
			result.add(cell);
		}
		result.add(route.outJunction());
		return result.iterator();
	}

	@Override
	public String toString() {
		return String.format("Itinerary %s", route);
	}
}
