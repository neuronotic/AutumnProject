package traffic.endtoend;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.SimulationTime.*;
import static traffic.VehicleMatchers.*;

import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import traffic.ConstantTemporalPattern;
import traffic.FlowBuilder;
import traffic.FlowGroupBuilder;
import traffic.ItineraryImpl;
import traffic.JourneyHistory;
import traffic.JourneyHistoryBuilder;
import traffic.Junction;
import traffic.JunctionFactory;
import traffic.NullCell;
import traffic.RoadNetwork;
import traffic.RoadNetworkBuilder;
import traffic.Segment;
import traffic.SegmentBuilder;
import traffic.Simulation;
import traffic.SimulationBuilder;
import traffic.TrafficModule;
import traffic.TripFactory;
import traffic.Vehicle;
import traffic.VehicleBuilder;
import traffic.VehicleManager;
import traffic.VehicleManagerBuilder;

import com.google.guiceberry.GuiceBerryModule;
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

	//@Rule public GuiceBerryRule guiceBerry = new GuiceBerryRule(TrafficTestModule.class);
	@Rule public MyGuiceBerryRule guiceBerry = new MyGuiceBerryRule();

	@Inject private JunctionFactory junctionFactory;
	@Inject private Provider<VehicleManagerBuilder> VehicleManagerBuilderProvider;
	@Inject private Provider<VehicleBuilder> vehicleBuilderProvider;
	@Inject private Provider<SegmentBuilder> segmentBuilderProvider;
	@Inject private Provider<RoadNetworkBuilder> roadNetworkBuilderProvider;
	@Inject private Provider<JourneyHistoryBuilder> JourneyHistoryBuilderProvider;
	@Inject private Provider<SimulationBuilder> simulationBuilderProvider;
	@Inject private Provider<FlowGroupBuilder> flowGroupBuilderProvider;
	@Inject private Provider<FlowBuilder> flowBuilderProvider;

	private Junction junction0, junction1, junction2, junction3;
	private Segment segment0, segment1, segment2;
	private RoadNetwork roadNetwork;
	private JourneyHistory history0, history1;
	private Vehicle vehicle0, vehicle1;
	private VehicleManager vehicleManager;

	@Before
	public void setUp() throws Exception {
		//logger.info(String.format("\n\nSETUP============"));

		createJunctions();
		createSegments();
		createRoadNetwork();
		vehicleManager = VehicleManagerBuilderProvider.get().make();
		createVehicles();
	}


	@Test
	public void runSimAfterAddingFlowGroupFor10StepsResultsIn4JourneyHistoriesToThatPoint() throws Exception {
		final Simulation sim = simulationBuilderProvider.get()
				.withRoadNetwork(roadNetwork)
				.withFlowGroup(flowGroupBuilderProvider.get()
						.withTemporalPattern(new ConstantTemporalPattern())
						.withFlow(flowBuilderProvider.get()
								.withItinerary(new ItineraryImpl(segment0, segment1))
								.withProbability(1)))
				.make();
		sim.step(10);
		assertThat(sim.getEndedJourneyHistories().size(), equalTo(4));
	}

	@Test
	public void newlyCreatedVehiclesRemainOffRoadNetworkUntilJunctionPullsThemIn() throws Exception {
		//logger.info(String.format("\n============newlyCreatedVehiclesRemainOffRoadNetworkUntilJunctionPullsThemIn"));
		final Simulation sim = simulationBuilderProvider.get()
				.withRoadNetwork(roadNetwork)
				.make();

		junction0.addVehicle(vehicle0);
		junction3.addVehicle(vehicle1);

		assertThat(vehicle0, isLocatedAt(new NullCell()));
		assertThat(vehicle1, isLocatedAt(new NullCell()));
		sim.step(1);
		assertThat(vehicle0, isLocatedAt(junction0));
		assertThat(vehicle1, isLocatedAt(junction3));
	}

	@Test
	public void VehicleManagerMaintainsLogOfJourneyHistories() throws Exception {
		//logger.info(String.format("\n============VehicleManagerMaintainsLogOfJourneyHistories"));
		vehicle0.startJourney();
		vehicle1.startJourney();

		vehicleManager.step(8);
		createJourneyHistories();
		assertThat(vehicleManager.getEndedJourneyHistories().size(), is(2));
		assertThat(vehicleManager.getEndedJourneyHistories(), containsInAnyOrder(history0, history1));
	}


	@Test
	public void onlyOneVehicleCanOccupyACellAtATime() throws Exception {
		//logger.info(String.format("\n============onlyOneVehicleCanOccupyACellAtATime"));
		vehicle0.startJourney();
		vehicle1.startJourney();

		vehicleManager.step(1);
		assertThat(vehicle0, isLocatedAt(junction0));
		assertThat(vehicle1, isLocatedAt(junction3));

		vehicleManager.step(2);
		assertThat(vehicle0, isLocatedAt(junction1));
		assertThat(vehicle1, isLocatedAt(segment1, 0));

		vehicleManager.step(1);
		assertThat(vehicle0, isLocatedAt(segment2,0));
		assertThat(vehicle1, isLocatedAt(junction1));
	}

	@Test
	public void tripAcrossTwoSegmentsOfYShapedNetworkWith3SegmentsTakesCorrectAmmountOfTime() throws Exception {
		//logger.info(String.format("\n============tripAcrossTwoSegmentsOfYShapedNetworkWith3SegmentsTakesCorrectAmmountOfTime"));
		vehicle0.startJourney();
		vehicleManager.step(7);
		assertThat(vehicle0, isLocatedAt(junction2));
		assertThat(vehicle0, hasJourneyTime(time(7)));
	}

	private void createVehicles() {
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
	}

	private void createRoadNetwork() {
		roadNetwork = roadNetworkBuilderProvider.get()
			.withSegment(segment0)
			.withSegment(segment1)
			.withSegment(segment2)
			.make();
	}

	private void createSegments() {
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
	}

	private SegmentBuilder segment() {
		return segmentBuilderProvider.get();
	}

	private void createJunctions() {
		junction0 = junctionFactory.createJunction("junction0");
		junction1 = junctionFactory.createJunction("junction1");
		junction2 = junctionFactory.createJunction("junction2");
		junction3 = junctionFactory.createJunction("junction3");
	}

	private void createJourneyHistories() {
		history0 = JourneyHistoryBuilderProvider.get()
				.withStartTime(time(0))
				.withEndTime(time(7))
				.withCellEntryTime(junction0, time(1))
				.withCellEntryTime(segment0.getCell(0),time(2))
				.withCellEntryTime(junction1, time(3))
				.withCellEntryTime(segment2.getCell(0),time(4))
				.withCellEntryTime(segment2.getCell(1),time(5))
				.withCellEntryTime(junction2, time(6))
				.make(vehicle0);

		history1 = JourneyHistoryBuilderProvider.get()
				.withStartTime(time(0))
				.withEndTime(time(8))
				.withCellEntryTime(junction3, time(1))
				.withCellEntryTime(segment1.getCell(0),time(2))
				.withCellEntryTime(junction1, time(4))
				.withCellEntryTime(segment2.getCell(0),time(5))
				.withCellEntryTime(segment2.getCell(1),time(6))
				.withCellEntryTime(junction2, time(7))
				.make(vehicle1);
	}
}