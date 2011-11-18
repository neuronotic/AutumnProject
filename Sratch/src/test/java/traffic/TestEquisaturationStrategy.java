package traffic;

import static java.util.Arrays.*;

import org.jmock.Expectations;
import org.jmock.Sequence;
import org.junit.Rule;
import org.junit.Test;

public class TestEquisaturationStrategy {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final LightsManager lightsManager = context.mock(LightsManager.class);
	private final Link link0 = link("link0");
	private final Link link1 = link("link1");
	private final Link link2 = link("link2");
	private final Sequence lightsChanging = context.sequence("lightsChanging");

	private final JunctionControllerStrategy equisaturationStrategy = new EquisaturationStrategy();

	@Test
	public void stepChangesLightsToLink2WhenItHasHighestCongestion() throws Exception {
		context.checking(new Expectations() {
			{
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
	public void stepChangesLightsToLink1WhenItHasHighestCongestion() throws Exception {
		context.checking(new Expectations() {
			{
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
