package traffic.endtoend;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.SimulationTime.*;
import static traffic.VehicleMatchers.*;

import java.util.logging.Logger;

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
import traffic.Link;
import traffic.LinkBuilder;
import traffic.Network;
import traffic.NetworkBuilder;
import traffic.NullCell;
import traffic.Simulation;
import traffic.SimulationBuilder;
import traffic.TrafficModule;
import traffic.Vehicle;
import traffic.VehicleBuilder;

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

	ConstantTemporalPattern constantTemporalPattern = new ConstantTemporalPattern(1);
	@Inject private JunctionFactory junctionFactory;
	@Inject private Provider<VehicleBuilder> vehicleBuilderProvider;
	@Inject private Provider<LinkBuilder> linkBuilderProvider;
	@Inject private Provider<NetworkBuilder> networkBuilderProvider;
	@Inject private Provider<JourneyHistoryBuilder> JourneyHistoryBuilderProvider;
	@Inject private Provider<SimulationBuilder> simulationBuilderProvider;
	@Inject private Provider<FlowGroupBuilder> flowGroupBuilderProvider;
	@Inject private Provider<FlowBuilder> flowBuilderProvider;

	private Junction junction0, junction1, junction2, junction3;
	private Link link0, link1, link2;
	private Network network;
	private JourneyHistory history0, history1;
	private Vehicle vehicle0, vehicle1;

	@Test
	public void runSimAfterAddingFlowGroupFor10StepsResultsIn4JourneyHistoriesToThatPoint() throws Exception {
		createJunctions();
		createLinks();
		createNetwork();
		final Simulation sim = simulationBuilder()
				.withFlowGroup(flowGroupBuilderProvider.get()
						.withTemporalPattern(constantTemporalPattern)
						.withFlow(flowBuilderProvider.get()
								.withItinerary(new ItineraryImpl(link0, link2))
								.withProbability(1.0)))
				.make();
		sim.step(10);
		assertThat(sim.statistics().getEndedJourneyHistories().size(), equalTo(4));
	}

	@Test
	public void newlyCreatedVehiclesRemainOffNetworkUntilJunctionPullsThemIn() throws Exception {
		createJunctions();
		createLinks();
		createNetwork();

		final Simulation sim = simulationBuilder()
				.make();
		createVehicles();
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
		createJunctions();
		createLinks();
		createNetwork();

		final Simulation sim = simulationBuilder()
				.make();
		createVehicles();
		sim.step(8);
		createJourneyHistories();
		assertThat(sim.statistics().getEndedJourneyHistories().size(), is(2));
		assertThat(sim.statistics().getEndedJourneyHistories(), containsInAnyOrder(history0, history1));
	}


	@Test
	public void onlyOneVehicleCanOccupyACellAtATime() throws Exception {
		//logger.info(String.format("\n============onlyOneVehicleCanOccupyACellAtATime"));
		createJunctions();
		createLinks();
		createNetwork();


		final Simulation sim = simulationBuilder()
				.make();
		createVehicles();
		sim.step(1);
		assertThat(vehicle0, isLocatedAt(junction0));
		assertThat(vehicle1, isLocatedAt(junction3));

		sim.step(2);
		assertThat(vehicle0, isLocatedAt(junction1));
		assertThat(vehicle1, isLocatedAt(link1, 0));

		sim.step(1);
		assertThat(vehicle0, isLocatedAt(link2,0));
		assertThat(vehicle1, isLocatedAt(junction1));
	}

	@Test
	public void tripAcrossTwoLinksOfYShapedNetworkWith3LinksTakesCorrectAmmountOfTime() throws Exception {
		//logger.info(String.format("\n============tripAcrossTwoLinksOfYShapedNetworkWith3LinksTakesCorrectAmmountOfTime"));
		createJunctions();
		createLinks();
		createNetwork();

		final Simulation sim = simulationBuilder()
				.make();
		createVehicles();
		sim.step(7);
		assertThat(vehicle0, isLocatedAt(junction2));
		assertThat(vehicle0, hasJourneyTime(time(7)));
	}

	private SimulationBuilder simulationBuilder() {
		return simulationBuilderProvider.get()
				.withNetwork(network);
	}

	private void createVehicles() {
		vehicle0 = vehicleBuilderProvider.get()
			.withName("vehicle0")
			.withItinerary(new ItineraryImpl(link0, link2))
			.make();
		vehicle1 = vehicleBuilderProvider.get()
			.withName("vehicle1")
			.withItinerary(new ItineraryImpl(link1, link2))
			.make();
	}

	private void createNetwork() {
		network = networkBuilderProvider.get()
			.withLink(link0)
			.withLink(link1)
			.withLink(link2)
			.make();
	}

	private void createLinks() {
		link0 = link()
			.withName("link0")
			.withInJunction(junction0)
			.withOutJunction(junction1)
			.withLength(1)
			.make();
		link1 = link()
			.withName("link1")
			.withInJunction(junction3)
			.withOutJunction(junction1)
			.withLength(1)
			.make();
		link2 = link()
			.withName("link2")
			.withInJunction(junction1)
			.withOutJunction(junction2)
			.withLength(2)
			.make();
	}

	private LinkBuilder link() {
		return linkBuilderProvider.get();
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
				.withEndTime(time(6))
				.withCellEntryTime(junction0, time(0))
				.withCellEntryTime(link0.getCell(0),time(1))
				.withCellEntryTime(junction1, time(2))
				.withCellEntryTime(link2.getCell(0),time(3))
				.withCellEntryTime(link2.getCell(1),time(4))
				.withCellEntryTime(junction2, time(5))
				.make(vehicle0);

		history1 = JourneyHistoryBuilderProvider.get()
				.withStartTime(time(0))
				.withEndTime(time(7))
				.withCellEntryTime(junction3, time(0))
				.withCellEntryTime(link1.getCell(0),time(1))
				.withCellEntryTime(junction1, time(3))
				.withCellEntryTime(link2.getCell(0),time(4))
				.withCellEntryTime(link2.getCell(1),time(5))
				.withCellEntryTime(junction2, time(6))
				.make(vehicle1);
	}
}