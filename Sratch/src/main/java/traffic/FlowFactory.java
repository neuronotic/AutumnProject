package traffic;

public interface FlowFactory {

	Flow create(Itinerary itinerary, double probability);

}
