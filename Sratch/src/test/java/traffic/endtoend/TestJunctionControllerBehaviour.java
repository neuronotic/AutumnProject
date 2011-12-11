package traffic.endtoend;

import static java.util.Arrays.*;
import static org.hamcrest.MatcherAssert.*;
import static traffic.SimulationTime.*;

import org.junit.Rule;
import org.junit.Test;

import traffic.EquisaturationBuilder;
import traffic.FlowBuilder;
import traffic.FlowGroupBuilder;
import traffic.Junction;
import traffic.JunctionBuilder;
import traffic.JunctionControllerBuilder;
import traffic.LinkBuilder;
import traffic.MaskedBinaryTemporalPattern;
import traffic.Network;
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

	@Test
	public void equisaturation() throws Exception {
		final JunctionControllerBuilder controllerBuilder = equisaturationBuilderProvider.get()
			.withPeriod(time(3));

		final Network network = network(controllerBuilder);

		final Simulation sim = simulation()
			.withNetwork(network)
			.withFlowGroup(flowGroupBuilderProvider.get()
				.withTemporalPattern(new MaskedBinaryTemporalPattern(timeKeeper, asList(1,1,1,0,0)))
				.withFlow(flowBuilderProvider.get()
					.withRouteSpecifiedByLinkNames(network, "link0")))
			.withFlowGroup(flowGroupBuilderProvider.get()
				.withTemporalPattern(new MaskedBinaryTemporalPattern(timeKeeper, asList(1,0,0,0,0)))
				.withFlow(flowBuilderProvider.get()
					.withRouteSpecifiedByLinkNames(network, "link1")))
			.make();
		sim.step(10);
		assertThat(sim, SimulationMatchers.hasJourneyHistoryOriginatingAtJunctionCount(junction0, 3));
		assertThat(sim, SimulationMatchers.hasJourneyHistoryOriginatingAtJunctionCount(junction2, 2));
	}

	private SimulationBuilder simulation() {
		return simulationBuilderProvider.get();
	}

	private Network network(final JunctionControllerBuilder controllerBuilder) {
		junction0 = junction("junction0", controllerBuilder);
		junction1 = junction("junction1", controllerBuilder);
		junction2 = junction("junction2", controllerBuilder);

		return networkBuilderProvider.get()
			.withLink(link()
				.withName("link0")
				.withInJunction(junction0)
				.withOutJunction(junction1)
				.withLength(2)
				.make())
			.withLink( link()
				.withName("link1")
				.withInJunction(junction2)
				.withOutJunction(junction1)
				.withLength(2)
				.make())
			.make();
	}

	private LinkBuilder link() {
		return linkBuilderProvider.get();
	}

	private Junction junction(final String junctionName, final JunctionControllerBuilder controllerBuilder) {
		return junctionBuilderProvider.get()
			.withName(junctionName)
			.withControllerBuilder(controllerBuilder)
			.make();
	}

//	private JunctionController equisaturationController() { return new JunctionControllerImpl(timeKeeper, new EquisaturationStrategy(), time(controllerPeriod)); }
//	private	JunctionController periodicDutyCycleController() { return new JunctionControllerImpl(timeKeeper, new PeriodicDutyCycleStrategy(), time(controllerPeriod)); }

}
