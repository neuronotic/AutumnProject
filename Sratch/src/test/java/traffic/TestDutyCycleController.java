package traffic;

import static java.util.Arrays.*;
import static traffic.SimulationTime.*;

import org.jmock.Expectations;
import org.jmock.Sequence;
import org.junit.Rule;
import org.junit.Test;

public class TestDutyCycleController {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final LightsManager lightsManager = context.mock(LightsManager.class);
	private final Link link0 = context.mock(Link.class, "link0");
	private final Link link1 = context.mock(Link.class, "link1");
	private final Link link2 = context.mock(Link.class, "link2");
	private final TimeKeeper timeKeeper = context.mock(TimeKeeper.class);
	private final Sequence lightsChanging = context.sequence("lightsChanging");

	private final JunctionController dutyCycleStrategy = new DutyCycleController(timeKeeper, time(5), time(0));

	@Test
	public void stepCyclesGreenOverLinksIfTimeIsHarmonicOfPeriod() throws Exception {
		context.checking(new Expectations() {
			{
				allowing(lightsManager).linksInOrderAdded(); will(returnValue(asList(link0, link1, link2)));
			}
		});
		timeKeeperExpectation(time(5));
		specifyExpectationsForLightsGoingGreenFor(link0);
		dutyCycleStrategy.step(lightsManager);

		timeKeeperExpectation(time(10));
		specifyExpectationsForLightsGoingGreenFor(link1);
		dutyCycleStrategy.step(lightsManager);

		timeKeeperExpectation(time(20));
		specifyExpectationsForLightsGoingGreenFor(link2);
		dutyCycleStrategy.step(lightsManager);

		timeKeeperExpectation(time(25));
		specifyExpectationsForLightsGoingGreenFor(link0);
		dutyCycleStrategy.step(lightsManager);
	}

	private void timeKeeperExpectation(final SimulationTime time) {
		context.checking(new Expectations() {
			{
				allowing(timeKeeper).currentTime(); will(returnValue(time));
			}
		});
	}

	private void specifyExpectationsForLightsGoingGreenFor(final Link link) {
		context.checking(new Expectations() {
			{
				oneOf(lightsManager).setAllRed(); inSequence(lightsChanging);
				oneOf(lightsManager).setGreen(link); inSequence(lightsChanging);
			}
		});
	}

}
