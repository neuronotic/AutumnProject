package traffic;

public interface FlowBuilder {
	Flow make();

	FlowBuilder withProbability(double probability);

	FlowBuilder withItinerary(Itinerary itinerary);

	FlowBuilder withRouteSpecifiedByLinkNames(Network network, String...linkNames);

	Itinerary itinerary();

}
