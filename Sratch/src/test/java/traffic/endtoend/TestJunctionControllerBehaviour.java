package traffic.endtoend;

import static java.util.Arrays.*;
import static org.hamcrest.MatcherAssert.*;
import static traffic.SimulationTime.*;

import org.junit.Rule;
import org.junit.Test;

import traffic.ConstantTemporalPattern;
import traffic.EquisaturationStrategy;
import traffic.FlowBuilder;
import traffic.FlowGroupBuilder;
import traffic.ItineraryImpl;
import traffic.Junction;
import traffic.JunctionBuilder;
import traffic.JunctionController;
import traffic.JunctionControllerImpl;
import traffic.Link;
import traffic.LinkBuilder;
import traffic.MaskedBinaryTemporalPattern;
import traffic.Network;
import traffic.NetworkBuilder;
import traffic.PeriodicDutyCycleStrategy;
import traffic.Simulation;
import traffic.SimulationBuilder;
import traffic.SimulationMatchers;
import traffic.TimeKeeper;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class TestJunctionControllerBehaviour {
	@Rule public MyGuiceBerryRule guiceBerry = new MyGuiceBerryRule();

	@Inject private Provider<SimulationBuilder> simulationBuilderProvider;
	@Inject private Provider<FlowGroupBuilder> flowGroupBuilderProvider;
	@Inject private Provider<FlowBuilder> flowBuilderProvider;
	@Inject private Provider<NetworkBuilder> networkBuilderProvider;
	@Inject private Provider<LinkBuilder> linkBuilderProvider;
	@Inject private Provider<JunctionBuilder> junctionBuilderProvider;

	@Inject private TimeKeeper timeKeeper;
	private Junction junction0, junction1, junction2;
	private Link link0, link1;
	private int controllerPeriod;

	@Test
	public void equisaturation() throws Exception {
		controllerPeriod = 3;
		final Simulation sim = simulationBuilder( equisaturationController())
				.withFlowGroup(flowGroupBuilderProvider.get()
					.withTemporalPattern(new MaskedBinaryTemporalPattern(timeKeeper, asList(1,1,1,0,0)))
					.withFlow(flowBuilderProvider.get()
						.withItinerary(new ItineraryImpl(link0))))
				.withFlowGroup(flowGroupBuilderProvider.get()
					.withTemporalPattern(new MaskedBinaryTemporalPattern(timeKeeper, asList(1,0,0,0,0)))
					.withFlow(flowBuilderProvider.get()
						.withItinerary(new ItineraryImpl(link1))))
				.make();
		sim.step(10);
		assertThat(sim, SimulationMatchers.hasJourneyHistoryOriginatingAtJunctionCount(junction0, 3));
		assertThat(sim, SimulationMatchers.hasJourneyHistoryOriginatingAtJunctionCount(junction2, 2));

	}

	@Test
	public void periodicDutyCycleSuccessfullyCyclesGreenLight() throws Exception {
		controllerPeriod = 3;
		final int stepsBeforeJunctionWithLights = 3;

		final Simulation sim = simulationBuilder( periodicDutyCycleController() )
				.withFlowGroup(flowGroupBuilderProvider.get()
					.withTemporalPattern(new ConstantTemporalPattern(1))
					.withFlow(flowBuilderProvider.get()
						.withItinerary(new ItineraryImpl(link0))))
				.make();
		sim.step(stepsBeforeJunctionWithLights+3*controllerPeriod+1);
		assertThat(sim, SimulationMatchers.hasJourneyHistoryOriginatingAtJunctionCount(junction0, controllerPeriod));
	}

	private SimulationBuilder simulationBuilder(final JunctionController junctionController) {
		return simulationBuilderProvider.get()
				.withNetwork(createNetwork(junctionController));
	}

	private Network createNetwork(final JunctionController junctionController) {
		createJunctions(junctionController);
		createLinks();
		return networkBuilderProvider.get()
			.withLink(link0)
			.withLink(link1)
			.make();
	}

	private void createLinks() {
		link0 = link()
			.withName("link0")
			.withInJunction(junction0)
			.withOutJunction(junction1)
			.withLength(2)
			.make();
		link1 = link()
			.withName("link1")
			.withInJunction(junction2)
			.withOutJunction(junction1)
			.withLength(2)
			.make();
	}

	private LinkBuilder link() {
		return linkBuilderProvider.get();
	}

	private void createJunctions(final JunctionController junctionController) {
		junction0 = junctionBuilderProvider.get()
			.withName("junction0")
			.withController(equisaturationController())
			.make();
		junction2 = junctionBuilderProvider.get()
			.withName("junction2")
			.withController(equisaturationController())
			.make();
		junction1 = junctionBuilderProvider.get()
			.withName("junction1")
			.withController( junctionController)
			.make();
	}

	private JunctionController equisaturationController() { return new JunctionControllerImpl(timeKeeper, new EquisaturationStrategy(), time(controllerPeriod)); }
	private	JunctionController periodicDutyCycleController() { return new JunctionControllerImpl(timeKeeper, new PeriodicDutyCycleStrategy(), time(controllerPeriod)); }

}
