package traffic.endtoend;

import static org.hamcrest.MatcherAssert.*;
import static snippet.RoadNetworkFactory.*;
import static snippet.RoadNetworkMatchers.*;
import static traffic.endtoend.JourneyPlanner.*;
import static traffic.endtoend.RoadUserFactory.*;

import org.junit.Test;

import snippet.Junction;
import snippet.RoadNetwork;
import snippet.Segment;
import snippet.Trip;

public class JourneyTimeTests {
	@Test
	public void tripAcrossSegmentOfLength5Takes6Timesteps() {
		final Junction junction0 = junction();
		final Junction junction1 = junction();
		final Segment segment = adjacent(junction0, junction1).connectedByCellChain(cellChainOfLength(5));
		final RoadNetwork roadNetwork = roadNetwork(segment);

		final Trip trip = tripFrom(junction0).to(junction1);

		final Itinerary itinerary = planItineraryForTrip(trip, roadNetwork);
		final RoadUser roadUser = roadUser(itinerary);

		roadNetwork.addRoadUser(roadUser);

		roadNetwork.step();
		roadNetwork.step();
		roadNetwork.step();
		roadNetwork.step();
		roadNetwork.step();
		roadNetwork.step();

		assertThat(roadUser, locatedAt(junction1));
	}
}

