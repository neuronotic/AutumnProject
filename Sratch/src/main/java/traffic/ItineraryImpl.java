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
	public Segment route() {
		return segments.get(0);
	}

	@Override
	public Iterator<Cell> iterator() {
		final List<Cell> result = new ArrayList<Cell>();
		for (final Iterator<Segment> iterator = segments.iterator(); iterator.hasNext();) {
			final Segment segment = iterator.next();
			result.add(segment.inJunction());
			for (final Cell cell : segment.cellChain()) {
				result.add(cell);
			}
			if (!iterator.hasNext()) {
				result.add(segment.outJunction());
			}
		}
		return result.iterator();
	}

	@Override
	public String toString() {
		return String.format("Itinerary %s", segments);
	}
}
