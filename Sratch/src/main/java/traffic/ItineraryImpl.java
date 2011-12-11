package traffic;

import static java.util.Arrays.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class ItineraryImpl implements Itinerary {
	private final List<Link> links;
	private final Iterator<Link> linkIterator;

	public ItineraryImpl(final Link...links) {
		this(asList(links));
	}

	@Inject public ItineraryImpl(@Assisted final List<Link> links) {
		this.links = links;
		linkIterator = links.iterator();
	}

	@Override
	public List<Link> route() {
		return links;
	}

	public List<Cell> getNextLinkCells() {
		return linkIterator.next().cells();
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

	@Override
	public Junction originJunction() {
		assert !links.isEmpty();
		return links.get(0).inJunction();
	}

}
