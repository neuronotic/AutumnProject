package traffic;

public interface FlowBuilder {
	Flow make();

	FlowBuilder withProbability(double probability);

	FlowBuilder withItinerary(Itinerary itinerary);

	FlowBuilder withRoute(Network network, String...linkNames);

}
