package traffic.endtoend;

import static org.hamcrest.MatcherAssert.*;
import static traffic.JourneyPlanner.*;
import static traffic.RoadNetworkFactory.*;
import static traffic.RoadNetworkMatchers.*;
import static traffic.VehicleMatchers.*;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import traffic.CellChainBuilder;
import traffic.CellChainBuilderImpl;
import traffic.Itinerary;
import traffic.Junction;
import traffic.JunctionFactory;
import traffic.JunctionImpl;
import traffic.RoadNetwork;
import traffic.Segment;
import traffic.SegmentFactory;
import traffic.SegmentImpl;
import traffic.Trip;
import traffic.Vehicle;
import traffic.VehicleFactory;
import traffic.VehicleFactoryImpl;
import traffic.VehicleManager;
import traffic.VehicleManagerImpl;

import com.google.guiceberry.GuiceBerryModule;
import com.google.guiceberry.junit4.GuiceBerryRule;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class TestVehicleMovement {

	public static class TrafficModule extends AbstractModule {
		@Override
		protected void configure() {
			install(new GuiceBerryModule());
			bind(VehicleFactory.class).to(VehicleFactoryImpl.class);
			bind(VehicleManager.class).to(VehicleManagerImpl.class);
			bind(CellChainBuilder.class).to(CellChainBuilderImpl.class);

			install(new FactoryModuleBuilder()
			    .implement(Junction.class, JunctionImpl.class)
			    .build(JunctionFactory.class));

			install(new FactoryModuleBuilder()
			    .implement(Segment.class, SegmentImpl.class)
			    .build(SegmentFactory.class));
		}
	};

	@Rule public GuiceBerryRule guiceBerry =
		      new GuiceBerryRule(TrafficModule.class);

	@Inject private VehicleFactory vehicleFactory;
	@Inject private VehicleManager vehicleManager;
	@Inject private JunctionFactory junctionFactory;
	@Inject private SegmentFactory segmentFactory;
	@Inject private Provider<CellChainBuilder> cellChainBuilderProvider;

	@Test
	@Ignore
	public void tripAcrossTwoSegmentNetworkWithLengths4And3Takes10Timesteps() throws Exception {
		final Junction junction0 = junctionFactory.createJunction("junction0");
		final Junction junction1 = junctionFactory.createJunction("junction1");
		final Junction junction2 = junctionFactory.createJunction("junction2");

		final Segment segment0 = segmentFactory.segment("segment0", junction0, cellChainBuilderProvider.get().cellChainOfLength(5), junction1);
		final Segment segment1 = segmentFactory.segment("segment1", junction1, cellChainBuilderProvider.get().cellChainOfLength(5), junction2);

		final RoadNetwork roadNetwork = roadNetwork(segment0, segment1);

		final Trip trip = tripFrom(junction0).to(junction2);

		final Itinerary itinerary = planItineraryForTrip(trip, roadNetwork);

		final Vehicle vehicle = vehicleFactory.createVehicle(itinerary);

		vehicleManager.addVehicle(vehicle);
		vehicleManager.step(10);

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

		final Itinerary itinerary = planItineraryForTrip(trip, roadNetwork);

		final Vehicle vehicle = vehicleFactory.createVehicle(itinerary);
		vehicleManager.addVehicle(vehicle);

		vehicleManager.step(7);

		assertThat(vehicle, isLocatedAt(junction1));
		assertThat(vehicle, hasJourneyTime(7));
	}
}

