package traffic.endtoend;

import static org.hamcrest.MatcherAssert.*;
import static traffic.JourneyPlanner.*;
import static traffic.RoadNetworkFactory.*;
import static traffic.RoadNetworkMatchers.*;
import static traffic.VehicleManagerFactory.*;
import static traffic.VehicleMatchers.*;

import org.junit.Test;

import traffic.Itinerary;
import traffic.Junction;
import traffic.RoadNetwork;
import traffic.Segment;
import traffic.Trip;
import traffic.Vehicle;
import traffic.VehicleFactory;
import traffic.VehicleFactoryImpl;
import traffic.VehicleManager;

public class TestVehicleMovement {
	private final VehicleFactory vehicleFactory = new VehicleFactoryImpl();

	@Test
	public void tripAcrossSingleSegmentNetworkOfLength5Takes7Timesteps() {
		final Junction junction0 = junction("junction0");
		final Junction junction1 = junction("junction1");

		final Segment segment = segment(junction0, cellChainOfLength(5), junction1);
		final RoadNetwork roadNetwork = roadNetwork(segment);

		final Trip trip = tripFrom(junction0).to(junction1);

		final Itinerary itinerary = planItineraryForTrip(trip, roadNetwork);

		final VehicleManager vehicleManager = vehicleManager();
		final Vehicle vehicle = vehicleFactory.createVehicle(itinerary);
		vehicleManager.addVehicle(vehicle);

		vehicleManager.step(7);

		assertThat(vehicle, isLocatedAt(junction1));
		assertThat(vehicle, hasJourneyTime(7));
	}
}

