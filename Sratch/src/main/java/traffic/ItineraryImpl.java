package traffic;

import static java.util.Arrays.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItineraryImpl implements Itinerary {
	private final List<Segment> segments;

	public ItineraryImpl(final Segment...segments) {
		this(asList(segments));
	}

	public ItineraryImpl(final List<Segment> segments) {
		this.segments = segments;
	}

	@Override
	public List<Segment> route() {
		return segments;
	}

	@Override
	public Iterator<Cell> iterator() {
		final List<Cell> result = new ArrayList<Cell>();
		for (final Segment segment : segments) {
			if (!result.isEmpty()) {
				result.remove(result.size() - 1);
			}
			result.addAll(segment.cells());
		}
		return result.iterator();
	}

	@Override
	public String toString() {
		return String.format("Itinerary %s", segments);
	}
}
