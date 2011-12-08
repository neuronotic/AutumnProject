package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.SimulationTime.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestFirstComeFirstServedBuilderImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final FirstComeFirstServedFactory firstComeFactory = context.mock(FirstComeFirstServedFactory.class);
	private final JunctionController junctionController = context.mock(JunctionController.class);
	private final SimulationTime period = time(42);

	private final FirstComeFirstServedBuilder firstComeBuilder = new FirstComeFirstServedBuilderImpl(firstComeFactory);

	@Test
	public void ControllerConstructed() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(firstComeFactory).create(); will(returnValue(junctionController));
			}
		});
		assertThat(firstComeBuilder.make(), is(junctionController));
	}
}
