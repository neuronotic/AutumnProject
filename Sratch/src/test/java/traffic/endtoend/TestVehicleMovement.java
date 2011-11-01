package traffic.endtoend;

import static org.hamcrest.MatcherAssert.*;
import static traffic.RoadNetworkFactory.*;
import static traffic.RoadNetworkMatchers.*;
import static traffic.VehicleMatchers.*;

import org.junit.Rule;
import org.junit.Test;

import traffic.CellChainBuilder;
import traffic.Itinerary;
import traffic.Junction;
import traffic.JunctionFactory;
import traffic.RoadNetwork;
import traffic.RouteFinder;
import traffic.RouteFinderFactory;
import traffic.Segment;
import traffic.SegmentFactory;
import traffic.Simulation;
import traffic.SimulationBuilder;
import traffic.TrafficModule;
import traffic.Trip;
import traffic.Vehicle;
import traffic.VehicleFactory;
import traffic.VehicleManager;

import com.google.guiceberry.GuiceBerryModule;
import com.google.guiceberry.junit4.GuiceBerryRule;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class TestVehicleMovement {

	public static class TrafficTestModule extends AbstractModule {
		@Override
		protected void configure() {
			install(new GuiceBerryModule());
			install(new TrafficModule());
		}
	};

	@Rule public GuiceBerryRule guiceBerry =
		      new GuiceBerryRule(TrafficTestModule.class);

	@Inject private VehicleFactory vehicleFactory;
	@Inject private VehicleManager vehicleManager;
	@Inject private JunctionFactory junctionFactory;
	@Inject private SegmentFactory segmentFactory;
	@Inject private Provider<CellChainBuilder> cellChainBuilderProvider;
	@Inject private RouteFinderFactory routeFinderFactory;
	@Inject private Provider<SimulationBuilder> simulationBuilderProvider;

	@Test
	public void tripAcrossTwoSegmentsOfYShapedNetworkWith3SegmentsTakesCorrectAmmountOfTime() throws Exception {
		final Junction junction0 = junctionFactory.createJunction("junction0");
		final Junction junction1 = junctionFactory.createJunction("junction1");
		final Junction junction2 = junctionFactory.createJunction("junction2");
		final Junction junction3 = junctionFactory.createJunction("junction3");

		final Segment segment0 = segmentFactory.segment("segment0", junction0, cellChainBuilderProvider.get().cellChainOfLength(4), junction1);
		final Segment segment1 = segmentFactory.segment("segment1", junction1, cellChainBuilderProvider.get().cellChainOfLength(3), junction2);
		final Segment segment2 = segmentFactory.segment("segment2", junction1, cellChainBuilderProvider.get().cellChainOfLength(5), junction3);

		final RoadNetwork roadNetwork = roadNetwork(segment0, segment1, segment2);

		final Trip trip = tripFrom(junction0).to(junction2);

		final RouteFinder routeFinder = routeFinderFactory.createShortestPathRouteFinder(roadNetwork);

		final Itinerary itinerary = routeFinder.calculateItinerary(trip);

		final Vehicle vehicle = vehicleFactory.createVehicle(itinerary);

		final Simulation simulation = simulationBuilderProvider.get()
				.withRoadNetwork(roadNetwork)
				.withVehicle(vehicle)
				.make();

		simulation.step(7);

		assertThat(vehicle, isLocatedAt(junction2));
		assertThat(vehicle, hasJourneyTime(10));

	}

	@Test
	public void tripAcrossTwoSegmentNetworkWithLengths4And3Takes10Timesteps() throws Exception {
		final Junction junction0 = junctionFactory.createJunction("junction0");
		final Junction junction1 = junctionFactory.createJunction("junction1");
		final Junction junction2 = junctionFactory.createJunction("junction2");

		final Segment segment0 = segmentFactory.segment("segment0", junction0, cellChainBuilderProvider.get().cellChainOfLength(4), junction1);
		final Segment segment1 = segmentFactory.segment("segment1", junction1, cellChainBuilderProvider.get().cellChainOfLength(3), junction2);

		final RoadNetwork roadNetwork = roadNetwork(segment0, segment1);

		final Trip trip = tripFrom(junction0).to(junction2);

		final RouteFinder routeFinder = routeFinderFactory.createShortestPathRouteFinder(roadNetwork);

		final Itinerary itinerary = routeFinder.calculateItinerary(trip);

		final Vehicle vehicle = vehicleFactory.createVehicle(itinerary);

		final Simulation simulation = simulationBuilderProvider.get()
				.withRoadNetwork(roadNetwork)
				.withVehicle(vehicle)
				.make();

		simulation.step(10);

		assertThat(vehicle, isLocatedAt(junction2));
		assertThat(vehicle, hasJourneyTime(10));
	}

	@Test
	public void tripAcrossSingleSegmentNetworkOfLength5Takes7Timesteps() {
		final Junction junction0 = junctionFactory.createJunction("junction0");
		final Junction junction1 = junctionFactory.createJunction("junction1");

		final Segment segment = segmentFactory.segment("segment0", junction0, cellChainBuilderProvider.get().cellChainOfLength(5), junction1);
		final RoadNetwork roadNetwork = roadNetwork(segment);

		final Trip trip = tripFrom(junction0).to(junction1);

		final RouteFinder routeFinder = routeFinderFactory.createShortestPathRouteFinder(roadNetwork);

		final Itinerary itinerary = routeFinder.calculateItinerary(trip);

		final Vehicle vehicle = vehicleFactory.createVehicle(itinerary);

		final Simulation simulation = simulationBuilderProvider.get()
			.withRoadNetwork(roadNetwork)
			.withVehicle(vehicle)
			.make();

		simulation.step(7);

		assertThat(vehicle, isLocatedAt(junction1));
		assertThat(vehicle, hasJourneyTime(7));
	}

}