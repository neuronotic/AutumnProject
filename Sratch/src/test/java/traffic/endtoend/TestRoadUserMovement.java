package traffic.endtoend;

import static org.hamcrest.MatcherAssert.*;
import static traffic.JourneyPlanner.*;
import static traffic.RoadNetworkFactory.*;
import static traffic.RoadNetworkMatchers.*;
import static traffic.RoadUserFactory.*;

import org.junit.Test;

import traffic.Itinerary;
import traffic.Junction;
import traffic.RoadNetwork;
import traffic.Segment;
import traffic.Trip;

public class TestRoadUserMovement {
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

		assertThat(roadUser, isLocatedAt(junction1));
		assertThat(roadUser, hasJourneyTime(6));
	}
}

