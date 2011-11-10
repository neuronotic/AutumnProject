package traffic.endtoend;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.SimulationTime.*;
import static traffic.VehicleMatchers.*;

import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import traffic.JourneyHistory;
import traffic.JourneyHistoryBuilder;
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

public class TestVehicleJourneys {
	@Inject Logger logger = Logger.getAnonymousLogger();

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
	@Inject private Provider<JourneyHistoryBuilder> JourneyHistoryBuilderProvider;

	private Junction junction0, junction1, junction2, junction3;
	private Segment segment0, segment1, segment2;
	private RoadNetwork roadNetwork;
	private Vehicle vehicle0, vehicle1;
	private VehicleManager manager;


	@Test
	public void VehicleManagerMaintainsLogOfJourneyHistories() throws Exception {
		manager.addVehicle(vehicle0);
		manager.addVehicle(vehicle1);
		vehicle0.startJourney();
		vehicle1.startJourney();

		manager.step(8);


		final JourneyHistory history0 = JourneyHistoryBuilderProvider.get()
				.withStartTime(time(0))
				.withEndTime(time(7))
				.withCellEntryTime(junction0, time(1))
				.withCellEntryTime(segment0.getCell(0),time(2))
				.withCellEntryTime(junction1, time(3))
				.withCellEntryTime(segment2.getCell(0),time(4))
				.withCellEntryTime(segment2.getCell(1),time(5))
				.withCellEntryTime(junction2, time(6))
				.make(vehicle0);

		final JourneyHistory history1 = JourneyHistoryBuilderProvider.get()
				.withStartTime(time(0))
				.withEndTime(time(8))
				.withCellEntryTime(junction3, time(1))
				.withCellEntryTime(segment1.getCell(0),time(2))
				.withCellEntryTime(junction1, time(4))
				.withCellEntryTime(segment2.getCell(0),time(5))
				.withCellEntryTime(segment2.getCell(1),time(6))
				.withCellEntryTime(junction2, time(7))
				.make(vehicle1);

		assertThat(manager.getEndedJourneyHistories().size(), is(2));
		assertThat(manager.getEndedJourneyHistories(), containsInAnyOrder(history0, history1));
	}

	@Test
	public void onlyOneVehicleCanOccupyACellAtATime() throws Exception {
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
	}

	@Test
	public void tripAcrossTwoSegmentsOfYShapedNetworkWith3SegmentsTakesCorrectAmmountOfTime() throws Exception {
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
		assertThat(vehicle0, hasJourneyTime(time(10)));

	}

	private SegmentBuilder segment() {
		return segmentBuilderProvider.get();
	}

	@Before
	public void setUp() throws Exception {
		junction0 = junctionFactory.createJunction("junction0");
		junction1 = junctionFactory.createJunction("junction1");
		junction2 = junctionFactory.createJunction("junction2");
		junction3 = junctionFactory.createJunction("junction3");

		segment0 = segment()
			.withName("segment0")
			.withInJunction(junction0)
			.withOutJunction(junction1)
			.withLength(1)
			.make();
		segment1 = segment()
			.withName("segment1")
			.withInJunction(junction3)
			.withOutJunction(junction1)
			.withLength(1)
			.make();
		segment2 = segment()
			.withName("segment2")
			.withInJunction(junction1)
			.withOutJunction(junction2)
			.withLength(2)
			.make();

		roadNetwork = roadNetworkBuilderProvider.get()
			.withSegment(segment0)
			.withSegment(segment1)
			.withSegment(segment2)
			.make();

		vehicle0 = vehicleBuilderProvider.get()
			.withName("vehicle0")
			.withRoadNetwork(roadNetwork)
			.withTrip(TripFactory.tripFrom(junction0).to(junction2))
			.make();
		vehicle1 = vehicleBuilderProvider.get()
			.withName("vehicle1")
			.withRoadNetwork(roadNetwork)
			.withTrip(TripFactory.tripFrom(junction3).to(junction2))
			.make();


		manager = VehicleManagerBuilderProvider.get().make();
	}

}