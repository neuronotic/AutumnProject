package traffic.endtoend;

import static org.hamcrest.MatcherAssert.*;
import static traffic.SimulationMatchers.*;
import static traffic.SimulationTime.*;

import org.junit.Rule;
import org.junit.Test;

import traffic.ConstantTemporalPattern;
import traffic.FlowBuilder;
import traffic.FlowGroupBuilder;
import traffic.ItineraryImpl;
import traffic.Junction;
import traffic.JunctionBuilder;
import traffic.JunctionFactory;
import traffic.Link;
import traffic.LinkBuilder;
import traffic.Network;
import traffic.NetworkBuilder;
import traffic.PeriodicDutyCycleBuilder;
import traffic.Simulation;
import traffic.SimulationBuilder;
import traffic.SimulationMatchers;
import traffic.SimulationTime;
import traffic.TrafficModule;

import com.google.guiceberry.GuiceBerryModule;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class TestJunctionBehaviour {
	public static class TrafficTestModule extends AbstractModule {
		@Override
		protected void configure() {
			install(new GuiceBerryModule());
			install(new TrafficModule());
		}
	};
	@Rule public MyGuiceBerryRule guiceBerry = new MyGuiceBerryRule();

	@Inject private JunctionFactory junctionFactory;
	@Inject private Provider<PeriodicDutyCycleBuilder> periodicDutyCycleBuilderProvider;
	@Inject private Provider<SimulationBuilder> simulationBuilderProvider;
	@Inject private Provider<FlowGroupBuilder> flowGroupBuilderProvider;
	@Inject private Provider<FlowBuilder> flowBuilderProvider;
	@Inject private Provider<NetworkBuilder> networkBuilderProvider;
	@Inject private Provider<LinkBuilder> linkBuilderProvider;
	@Inject private Provider<JunctionBuilder> junctionBuilderProvider;

	private final ConstantTemporalPattern constantTemporalPattern = new ConstantTemporalPattern(1.0);

	private Junction junction0, junction1;
	private Link link0;
	private int dutyCyclePeriod;

	@Test
	public void journeysAcrossJunctionWith() throws Exception {
		dutyCyclePeriod = 3;
		final Simulation sim = simulationBuilder()
				.withFlowGroup(flowGroupBuilderProvider.get()
					.withTemporalPattern(constantTemporalPattern.withModifier(1))
					.withFlow(flowBuilderProvider.get()
						.withItinerary(new ItineraryImpl(link0))
						.withProbability(1.0)))
				.make();
		sim.step(3);
		final SimulationTime lastTimeBeforeNoArrivals = sim.time();
		assertThat(sim, hasJourneyHistoryCount(0));
		sim.step(dutyCyclePeriod);
		assertThat(sim, hasJourneyHistoryCount(dutyCyclePeriod));
		assertThat(sim, SimulationMatchers.hasJourneyHistoryOriginatingAtJunctionCount(junction0, dutyCyclePeriod));
		sim.step(dutyCyclePeriod);
		assertThat(sim, hasJourneyHistoryCount(dutyCyclePeriod));
		assertThat(sim, SimulationMatchers.hasJourneyHistoryOriginatingAtJunctionCount(junction0, dutyCyclePeriod));


	}

	private SimulationBuilder simulationBuilder() {
		return simulationBuilderProvider.get()
				.withNetwork(createNetwork());
	}

	private Network createNetwork() {
		createJunctions();
		createLinks();
		return networkBuilderProvider.get()
			.withLink(link0)
			.make();
	}

	private void createLinks() {
		link0 = link()
			.withName("link0")
			.withInJunction(junction0)
			.withOutJunction(junction1)
			.withLength(1)
			.make();
	}

	private LinkBuilder link() {
		return linkBuilderProvider.get();
	}

	private void createJunctions() {
		junction0 = junctionBuilderProvider.get()
			.withName("junction0")
			.make();

		junction1 = junctionBuilderProvider.get()
			.withName("junction1")
			.withJunctionControllerStrategy( periodicDutyCycleBuilderProvider.get()
				.withPeriod(time(dutyCyclePeriod)))
			.make();
	}
}
