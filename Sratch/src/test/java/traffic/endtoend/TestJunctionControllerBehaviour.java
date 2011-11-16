package traffic.endtoend;

import static org.hamcrest.MatcherAssert.*;
import static traffic.SimulationTime.*;

import org.junit.Rule;
import org.junit.Test;

import traffic.ConstantTemporalPattern;
import traffic.FlowBuilder;
import traffic.FlowGroupBuilder;
import traffic.ItineraryImpl;
import traffic.Junction;
import traffic.JunctionBuilder;
import traffic.JunctionControllerStrategyBuilder;
import traffic.JunctionFactory;
import traffic.Link;
import traffic.LinkBuilder;
import traffic.Network;
import traffic.NetworkBuilder;
import traffic.Simulation;
import traffic.SimulationBuilder;
import traffic.SimulationMatchers;
import traffic.TimeKeeper;
import traffic.TrafficModule;

import com.google.guiceberry.GuiceBerryModule;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class TestJunctionControllerBehaviour {
	public static class TrafficTestModule extends AbstractModule {
		@Override
		protected void configure() {
			install(new GuiceBerryModule());
			install(new TrafficModule());
		}
	};
	@Rule public MyGuiceBerryRule guiceBerry = new MyGuiceBerryRule();

	@Inject private JunctionFactory junctionFactory;
	@Inject @Named("PeriodicDutyCycleBuilder") private Provider<JunctionControllerStrategyBuilder> periodicDutyCycleBuilderProvider;
	@Inject @Named("EquisaturationBuilder") private Provider<JunctionControllerStrategyBuilder> equisaturationBuilderProvider;

	@Inject private Provider<SimulationBuilder> simulationBuilderProvider;
	@Inject private Provider<FlowGroupBuilder> flowGroupBuilderProvider;
	@Inject private Provider<FlowBuilder> flowBuilderProvider;
	@Inject private Provider<NetworkBuilder> networkBuilderProvider;
	@Inject private Provider<LinkBuilder> linkBuilderProvider;
	@Inject private Provider<JunctionBuilder> junctionBuilderProvider;

	@Inject private TimeKeeper timeKeeper;
	private Junction junction0, junction1, junction2;
	private Link link0, link1;
	private int dutyCyclePeriod;

	@Test
	public void equiSaturation() throws Exception {
		dutyCyclePeriod = 3;
		final int stepsBeforeJunctionWithLights = 3;
		final Simulation sim = simulationBuilder( equisaturationBuilderProvider.get().withPeriod(time(dutyCyclePeriod)))
				.withFlowGroup(flowGroupBuilderProvider.get()
					.withTemporalPattern(new ConstantTemporalPattern(1))
					.withFlow(flowBuilderProvider.get()
						.withItinerary(new ItineraryImpl(link0)))
					.withFlow(flowBuilderProvider.get()
						.withItinerary(new ItineraryImpl(link1))))
				.make();

		sim.step(stepsBeforeJunctionWithLights+3*dutyCyclePeriod+1);
		assertThat(sim, SimulationMatchers.hasJourneyHistoryOriginatingAtJunctionCount(junction0, dutyCyclePeriod));
	}

	@Test
	public void periodicDutyCycleSuccessfullyCyclesGreenLight() throws Exception {
		dutyCyclePeriod = 3;
		final int stepsBeforeJunctionWithLights = 3;

		final Simulation sim = simulationBuilder( periodicDutyCycleBuilderProvider.get().withPeriod(time(dutyCyclePeriod)) )
				.withFlowGroup(flowGroupBuilderProvider.get()
					.withTemporalPattern(new ConstantTemporalPattern(1))
					.withFlow(flowBuilderProvider.get()
						.withItinerary(new ItineraryImpl(link0))))
				.make();
		sim.step(stepsBeforeJunctionWithLights+3*dutyCyclePeriod+1);
		assertThat(sim, SimulationMatchers.hasJourneyHistoryOriginatingAtJunctionCount(junction0, dutyCyclePeriod));
	}

	private SimulationBuilder simulationBuilder(final JunctionControllerStrategyBuilder controllerStrategy) {
		return simulationBuilderProvider.get()
				.withNetwork(createNetwork(controllerStrategy));
	}

	private Network createNetwork(final JunctionControllerStrategyBuilder controllerStrategy) {
		createJunctions(controllerStrategy);
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

	private void createJunctions(final JunctionControllerStrategyBuilder controllerStrategy) {
		junction0 = junctionBuilderProvider.get()
			.withName("junction0")
			.make();
		junction2 = junctionBuilderProvider.get()
			.withName("junction2")
			.make();
		junction1 = junctionBuilderProvider.get()
			.withName("junction1")
			.withJunctionControllerStrategy( controllerStrategy)
			.make();
	}
}
