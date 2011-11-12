package traffic;

import static java.util.Arrays.*;

import java.util.ArrayList;
import java.util.List;

public class ItineraryImpl implements Itinerary {
	private final List<Link> links;

	public ItineraryImpl(final Link...links) {
		this(asList(links));
	}

	public ItineraryImpl(final List<Link> links) {
		this.links = links;
	}

	@Override
	public List<Link> route() {
		return links;
	}

	@Override
	public List<Cell> cells() {
		final List<Cell> result = new ArrayList<Cell>();
		for (final Link link : links) {
			if (!result.isEmpty()) {
				result.remove(result.size() - 1);
			}
			result.addAll(link.cells());
		}
		return result;
	}

	@Override
	public String toString() {
		return String.format("Itinerary %s", links);
	}

}
