package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.SimulationTime.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestEquisaturationBuilderImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final EquisaturationFactory equisaturationFactory = context.mock(EquisaturationFactory.class);
	private final JunctionController junctionController = context.mock(JunctionController.class);
	private final SimulationTime period = time(42);
	private final SimulationTime switchingDelay = time(7);

	private final EquisaturationBuilder equisaturationBuilder = new EquisaturationBuilderImpl(equisaturationFactory);

	@Test
	public void ControllerConstructedWithPeriod() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(equisaturationFactory).create(period, switchingDelay); will(returnValue(junctionController));
			}
		});
		assertThat(equisaturationBuilder.withPeriod(period).withSwitchingDelay(switchingDelay).make(), is(junctionController));
	}

}
