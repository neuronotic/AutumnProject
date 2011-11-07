package traffic.endtoend;

import static org.hamcrest.MatcherAssert.*;
import static traffic.VehicleMatchers.*;

import org.junit.Rule;
import org.junit.Test;

import traffic.Junction;
import traffic.JunctionFactory;
import traffic.RoadNetwork;
import traffic.RoadNetworkBuilder;
import traffic.Segment;
import traffic.SegmentBuilder;
import traffic.TrafficModule;
import traffic.TripFactory;
import traffic.Vehicle;
import traffic.VehicleBuilder;
import traffic.VehicleManager;
import traffic.VehicleManagerBuilder;

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

	@Inject private JunctionFactory junctionFactory;
	@Inject private Provider<VehicleManagerBuilder> VehicleManagerBuilderProvider;
	@Inject private Provider<VehicleBuilder> vehicleBuilderProvider;
	@Inject private Provider<SegmentBuilder> segmentBuilderProvider;
	@Inject private Provider<RoadNetworkBuilder> roadNetworkBuilderProvider;


	@Test
	public void onlyOneVehicleCanOccupyACellAtATime() throws Exception {
		final Junction junction0 = junctionFactory.createJunction("junction0");
		final Junction junction1 = junctionFactory.createJunction("junction1");
		final Junction junction2 = junctionFactory.createJunction("junction2");
		final Junction junction3 = junctionFactory.createJunction("junction3");

		final Segment segment0 = segment()
			.withName("segment0")
			.withInJunction(junction0)
			.withOutJunction(junction1)
			.withLength(1)
			.make();
		final Segment segment1 = segment()
			.withName("segment1")
			.withInJunction(junction3)
			.withOutJunction(junction1)
			.withLength(1)
			.make();
		final Segment segment2 = segment()
			.withName("segment2")
			.withInJunction(junction1)
			.withOutJunction(junction2)
			.withLength(2)
			.make();

		final RoadNetwork roadNetwork = roadNetworkBuilderProvider.get()
			.withSegment(segment0)
			.withSegment(segment1)
			.withSegment(segment2)
			.make();


		final Vehicle vehicle0 = vehicleBuilderProvider.get()
				.withName("vehicle0")
				.withRoadNetwork(roadNetwork)
				.withTrip(TripFactory.tripFrom(junction0).to(junction2))
				.make();
			final Vehicle vehicle1 = vehicleBuilderProvider.get()
				.withName("vehicle1")
				.withRoadNetwork(roadNetwork)
				.withTrip(TripFactory.tripFrom(junction3).to(junction2))
				.make();


			final VehicleManager manager = VehicleManagerBuilderProvider.get().make();
			manager.addVehicle(vehicle0);
			manager.addVehicle(vehicle1);

			manager.step(1);
			assertThat(vehicle0, isLocatedAt(junction0));
			assertThat(vehicle1, isLocatedAt(junction3));

			manager.step(2);
			assertThat(vehicle0, isLocatedAt(junction1));
			assertThat(vehicle1, isLocatedAt(segment1, 0));

			manager.step(1);
			assertThat(vehicle0, isLocatedAt(segment2,0));
			assertThat(vehicle1, isLocatedAt(junction1));

			manager.step(1);
			assertThat(vehicle0, isLocatedAt(junction2));
			assertThat(vehicle1, isLocatedAt(segment2, 0));

			manager.step(1);
			assertThat(vehicle1, isLocatedAt(junction2));
	}



	@Test
	public void supportsMultipleVehiclesTakingSameTripOnNetwork1TimestepApart() throws Exception {
		final Junction junction0 = junctionFactory.createJunction("junction0");
		final Junction junction1 = junctionFactory.createJunction("junction1");

		final RoadNetwork roadNetwork = roadNetworkBuilderProvider.get()
			.withSegment(segment()
				.withName("segment0")
				.withInJunction(junction0)
				.withOutJunction(junction1)
				.withLength(5))
			.make();


		final Vehicle vehicle0 = vehicleBuilderProvider.get()
			.withName("vehicle0")
			.withRoadNetwork(roadNetwork)
			.withTrip(TripFactory.tripFrom(junction0).to(junction1))
			.make();
		final Vehicle vehicle1 = vehicleBuilderProvider.get()
			.withName("vehicle1")
			.withRoadNetwork(roadNetwork)
			.withTrip(TripFactory.tripFrom(junction0).to(junction1))
			.make();


		final VehicleManager manager = VehicleManagerBuilderProvider.get().make();
		manager.addVehicle(vehicle0);
		manager.step(1);
		manager.addVehicle(vehicle1);
		manager.step(6);

		assertThat(vehicle0, isLocatedAt(junction1));
		assertThat(vehicle0, hasJourneyTime(7));
		assertThat(vehicle1, hasJourneyTime(6));

		manager.step(1);
		assertThat(vehicle1, isLocatedAt(junction1));
		assertThat(vehicle1, hasJourneyTime(7));
	}

	@Test
	public void tripAcrossTwoSegmentsOfYShapedNetworkWith3SegmentsTakesCorrectAmmountOfTime() throws Exception {
		final Junction junction0 = junctionFactory.createJunction("junction0");
		final Junction junction1 = junctionFactory.createJunction("junction1");
		final Junction junction2 = junctionFactory.createJunction("junction2");
		final Junction junction3 = junctionFactory.createJunction("junction3");

		final RoadNetwork roadNetwork = roadNetworkBuilderProvider.get()
			.withSegment(segment()
				.withName("segment0")
				.withInJunction(junction0)
				.withOutJunction(junction1)
				.withLength(4))
			.withSegment(segment()
				.withName("segment1")
				.withInJunction(junction1)
				.withOutJunction(junction2)
				.withLength(3))
			.withSegment(segment()
				.withName("segment2")
				.withInJunction(junction1)
				.withOutJunction(junction3)
				.withLength(3))
			.make();

		final Vehicle vehicle0 = vehicleBuilderProvider.get()
			.withRoadNetwork(roadNetwork)
			.withTrip(TripFactory.tripFrom(junction0).to(junction2))
			.make();

		final VehicleManager manager = VehicleManagerBuilderProvider.get().make();
		manager.addVehicle(vehicle0);
		manager.step(10);

		assertThat(vehicle0, isLocatedAt(junction2));
		assertThat(vehicle0, hasJourneyTime(10));

	}

	@Test
	public void tripAcrossTwoSegmentNetworkWithLengths4And3Takes10Timesteps() throws Exception {
		final Junction junction0 = junctionFactory.createJunction("junction0");
		final Junction junction1 = junctionFactory.createJunction("junction1");
		final Junction junction2 = junctionFactory.createJunction("junction2");

		final RoadNetwork roadNetwork = roadNetworkBuilderProvider.get()
			.withSegment(segment()
				.withName("segment0")
				.withInJunction(junction0)
				.withOutJunction(junction1)
				.withLength(4))
			.withSegment(segment()
				.withName("segment1")
				.withInJunction(junction1)
				.withOutJunction(junction2)
				.withLength(3))
			.make();



		final Vehicle vehicle0 = vehicleBuilderProvider.get()
			.withRoadNetwork(roadNetwork)
			.withTrip(TripFactory.tripFrom(junction0).to(junction2))
			.make();

		final VehicleManager manager = VehicleManagerBuilderProvider.get().make();
		manager.addVehicle(vehicle0);
		manager.step(10);

		assertThat(vehicle0, isLocatedAt(junction2));
		assertThat(vehicle0, hasJourneyTime(10));
	}

	@Test
	public void tripAcrossSingleSegmentNetworkOfLength5Takes7Timesteps() {
		final Junction junction0 = junctionFactory.createJunction("junction0");
		final Junction junction1 = junctionFactory.createJunction("junction1");

		final RoadNetwork roadNetwork = roadNetworkBuilderProvider.get()
			.withSegment(segment()
				.withName("segment0")
				.withInJunction(junction0)
				.withOutJunction(junction1)
				.withLength(5))
			.make();


		final Vehicle vehicle0 = vehicleBuilderProvider.get()
			.withRoadNetwork(roadNetwork)
			.withTrip(TripFactory.tripFrom(junction0).to(junction1))
			.make();


		final VehicleManager manager = VehicleManagerBuilderProvider.get().make();
		manager.addVehicle(vehicle0);
		manager.step(7);

		assertThat(vehicle0, isLocatedAt(junction1));
		assertThat(vehicle0, hasJourneyTime(7));
	}

	private SegmentBuilder segment() {
		return segmentBuilderProvider.get();
	}



}