package traffic;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

public class FlowBuilderImpl implements FlowBuilder {

	private double probability = 1.0;
	private Itinerary itinerary;
	private final FlowFactory flowFactory;
	private final ItineraryFactory itineraryFactory;

	@Inject public FlowBuilderImpl(final FlowFactory flowFactory, final ItineraryFactory itineraryFactory) {
		this.flowFactory = flowFactory;
		this.itineraryFactory = itineraryFactory;
	}

	@Override
	public Flow make() {
		return flowFactory.create(itinerary, probability);
	}

	@Override
	public FlowBuilder withProbability(final double probability) {
		this.probability = probability;
		return this;
	}

	@Override
	public FlowBuilder withItinerary(final Itinerary itinerary) {
		this.itinerary = itinerary;
		return this;
	}

	@Override
	public Itinerary itinerary() {
		return itinerary;
	}

	@Override
	public FlowBuilder withRouteSpecifiedByLinkNames(final Network network,
			final String... linkNames) {
		itinerary = itineraryFactory.create(resolveLinksFromNames(network, linkNames));
		return this;
	}

	private List<Link> resolveLinksFromNames(final Network network,
			final String... linkNames) {
		final List<Link> links = new ArrayList<Link>();
		for (final String linkName : linkNames) {
			links.add(network.linkNamed(linkName));
		}
		return links;
	}

}
