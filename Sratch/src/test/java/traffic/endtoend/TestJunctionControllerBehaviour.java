package traffic.endtoend;

import static java.util.Arrays.*;
import static org.hamcrest.MatcherAssert.*;
import static traffic.SimulationTime.*;

import org.junit.Rule;
import org.junit.Test;

import traffic.EquisaturationBuilder;
import traffic.FlowBuilder;
import traffic.FlowGroupBuilder;
import traffic.ItineraryImpl;
import traffic.Junction;
import traffic.JunctionBuilder;
import traffic.JunctionControllerBuilder;
import traffic.Link;
import traffic.LinkBuilder;
import traffic.MaskedBinaryTemporalPattern;
import traffic.NetworkBuilder;
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
	@Inject private Provider<EquisaturationBuilder> equisaturationBuilderProvider;

	@Inject private TimeKeeper timeKeeper;
	private Junction junction0, junction1, junction2;
	private Link link0, link1;

	@Test
	public void equisaturation() throws Exception {
		final JunctionControllerBuilder controllerBuilder = equisaturationBuilderProvider.get()
			.withPeriod(time(3));

		junction0 = junction()
			.withName("junction0")
			.withControllerBuilder(controllerBuilder)
			.make();
		junction2 = junction()
			.withName("junction2")
			.withControllerBuilder(controllerBuilder)
			.make();
		junction1 = junction()
			.withName("junction1")
			.withControllerBuilder(controllerBuilder)
			.make();
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
		final Simulation sim = simulation()
			.withNetwork( network()
				.withLink( link0 )
				.withLink( link1 )
				.make() )
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

	private SimulationBuilder simulation() {
		return simulationBuilderProvider.get();
	}

	private NetworkBuilder network() {
		return networkBuilderProvider.get();
	}

	private LinkBuilder link() {
		return linkBuilderProvider.get();
	}

	private JunctionBuilder junction() {
		return junctionBuilderProvider.get();
	}

//	private JunctionController equisaturationController() { return new JunctionControllerImpl(timeKeeper, new EquisaturationStrategy(), time(controllerPeriod)); }
//	private	JunctionController periodicDutyCycleController() { return new JunctionControllerImpl(timeKeeper, new PeriodicDutyCycleStrategy(), time(controllerPeriod)); }

}
