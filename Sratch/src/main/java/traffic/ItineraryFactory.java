package traffic;

import java.util.List;

public interface ItineraryFactory {

	Itinerary create(List<Link> links);

}
