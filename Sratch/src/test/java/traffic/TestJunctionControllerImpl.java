package traffic;

import static traffic.SimulationTime.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestJunctionControllerImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final TimeKeeper timeKeeper = context.mock(TimeKeeper.class);
	private final JunctionControllerStrategy junctionControllerStrategy = context.mock(JunctionControllerStrategy.class);
	private final LightsManager lightsManager = context.mock(LightsManager.class);

	private final SimulationTime period = time(3);

	private final JunctionController junctionController = new JunctionControllerImpl(timeKeeper, junctionControllerStrategy, period);

	@Test
	public void onlyDelegatesStepToStrategyAtTimesCoincidingWithPeriod() throws Exception {
		setTimeKeeperExpectation(time(2));
		junctionController.step(lightsManager);

		setTimeKeeperExpectation(time(4));
		junctionController.step(lightsManager);


		setTimeKeeperExpectation(time(3));
		context.checking(new Expectations() {
			{
				oneOf(junctionControllerStrategy).step(lightsManager);
			}
		});
		junctionController.step(lightsManager);

	}

	private void setTimeKeeperExpectation(final SimulationTime time) {
		context.checking(new Expectations() {
			{
				oneOf(timeKeeper).currentTime(); will(returnValue(time));
			}
		});
	}
}
