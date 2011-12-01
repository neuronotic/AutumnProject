package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.SimulationTime.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestDutyCycleBuilderImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final DutyCycleFactory dutyCycleFactory = context.mock(DutyCycleFactory.class);
	private final JunctionController junctionController = context.mock(JunctionController.class);
	private final SimulationTime period = time(42);

	private final DutyCycleBuilder dutyCycleBuilder = new DutyCycleBuilderImpl(dutyCycleFactory);

	@Test
	public void ControllerConstructedWithPeriod() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(dutyCycleFactory).create(period); will(returnValue(junctionController));
			}
		});
		assertThat(dutyCycleBuilder.withPeriod(period).make(), is(junctionController));
	}
}
