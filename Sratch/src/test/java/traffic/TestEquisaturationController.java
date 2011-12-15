package traffic;

import static java.util.Arrays.*;
import static traffic.SimulationTime.*;

import org.jmock.Expectations;
import org.jmock.Sequence;
import org.junit.Rule;
import org.junit.Test;

public class TestEquisaturationController {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final LightsManager lightsManager = context.mock(LightsManager.class);
	private final Link link0 = link("link0");
	private final Link link1 = link("link1");
	private final Link link2 = link("link2");
	private final Sequence lightsChanging = context.sequence("lightsChanging");
	private final TimeKeeper timeKeeper = context.mock(TimeKeeper.class);
	private final SimulationTime period = time(5);

	private final JunctionController equisaturationStrategy = new EquisaturationController(timeKeeper, period, time(0));

	@Test
	public void stepDoesNotChangeLightsIfTimeIsNotHarmonicOfPeriod() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(timeKeeper).currentTime(); will(returnValue(time(6)));
			}
		});
		equisaturationStrategy.step(lightsManager);
	}

	@Test
	public void stepChangesLightsToLink2WhenItHasHighestCongestionIfTimeIsHarmonicOfPeriod() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(timeKeeper).currentTime(); will(returnValue(time(0)));
				allowing(lightsManager).linksInOrderAdded(); will(returnValue(asList(link0, link1, link2)));
				allowing(link0).congestion(); will(returnValue(0.3));
				allowing(link1).congestion(); will(returnValue(0.1));
				allowing(link2).congestion(); will(returnValue(0.5));
			}
		});
		specifyExpectationsForLightsGoingGreenFor(link2);
		equisaturationStrategy.step(lightsManager);
	}

	@Test
	public void stepChangesLightsToLink1WhenItHasHighestCongestionIfTimeIsHarmonicOfPeriod() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(timeKeeper).currentTime(); will(returnValue(time(5)));
				allowing(lightsManager).linksInOrderAdded(); will(returnValue(asList(link0, link1, link2)));
				allowing(link0).congestion(); will(returnValue(0.3));
				allowing(link1).congestion(); will(returnValue(0.7));
				allowing(link2).congestion(); will(returnValue(0.5));
			}
		});
		specifyExpectationsForLightsGoingGreenFor(link1);
		equisaturationStrategy.step(lightsManager);

	}

	private void specifyExpectationsForLightsGoingGreenFor(final Link link) {
		context.checking(new Expectations() {
			{
				oneOf(lightsManager).setAllRed(); inSequence(lightsChanging);
				oneOf(lightsManager).setGreen(link); inSequence(lightsChanging);
			}
		});
	}

	private Link link(final String mockName) {
		final Link link = context.mock(Link.class, mockName);
		context.checking(new Expectations() {
			{
				allowing(link).name();
			}
		});
		return link;
	}
}
