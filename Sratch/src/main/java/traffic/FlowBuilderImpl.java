package traffic;

import com.google.inject.Inject;

public class FlowBuilderImpl implements FlowBuilder {

	private double probability;
	private Itinerary itinerary;
	private final FlowFactory flowFactory;

	@Inject public FlowBuilderImpl(final FlowFactory flowFactory) {
		this.flowFactory = flowFactory;
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
}
