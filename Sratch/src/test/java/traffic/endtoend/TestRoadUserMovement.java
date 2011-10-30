package traffic.endtoend;

import static org.hamcrest.MatcherAssert.*;
import static traffic.JourneyPlanner.*;
import static traffic.RoadNetworkFactory.*;
import static traffic.RoadNetworkMatchers.*;
import static traffic.RoadUserManagerFactory.*;
import static traffic.RoadUserMatchers.*;

import org.junit.Test;

import traffic.Itinerary;
import traffic.Junction;
import traffic.RoadNetwork;
import traffic.RoadUser;
import traffic.RoadUserFactory;
import traffic.RoadUserFactoryImpl;
import traffic.RoadUserManager;
import traffic.Segment;
import traffic.Trip;

public class TestRoadUserMovement {
	private final RoadUserFactory roadUserFactory = new RoadUserFactoryImpl();

	@Test
	public void tripAcrossSingleSegmentNetworkOfLength5Takes7Timesteps() {
		final Junction junction0 = junction("junction0");
		final Junction junction1 = junction("junction1");
		final Segment segment = segment(junction0, cellChainOfLength(5), junction1);
		final RoadNetwork roadNetwork = roadNetwork(segment);

		final Trip trip = tripFrom(junction0).to(junction1);

		final Itinerary itinerary = planItineraryForTrip(trip, roadNetwork);

		final RoadUserManager roadUserManager = roadUserManager();
		final RoadUser roadUser = roadUserFactory.createRoadUser(itinerary);
		roadUserManager.addRoadUser(roadUser);

		roadUserManager.step();
		roadUserManager.step();
		roadUserManager.step();
		roadUserManager.step();
		roadUserManager.step();
		roadUserManager.step();
		roadUserManager.step();

		assertThat(roadUser, isLocatedAt(junction1));
		assertThat(roadUser, hasJourneyTime(7));
	}
}

