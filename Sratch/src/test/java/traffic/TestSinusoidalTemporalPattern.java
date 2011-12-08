package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.SimulationTime.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestSinusoidalTemporalPattern {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final TimeKeeper timeKeeper = context.mock(TimeKeeper.class);
	private final TemporalPattern pattern = new SinusoidalTemporalPattern(timeKeeper, time(50));

	@Test
	public void correct() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(timeKeeper).currentTime(); will(returnValue(time(25)));
			}
		});
		assertThat(pattern.modifier(), is(1.0));

		context.checking(new Expectations() {
			{
				oneOf(timeKeeper).currentTime(); will(returnValue(time(50)));
			}
		});
		assertThat(pattern.modifier(), is(0.5));

		context.checking(new Expectations() {
			{
				oneOf(timeKeeper).currentTime(); will(returnValue(time(20)));
			}
		});
		assertThat(pattern.modifier(), is(0.5+0.5*Math.sin(Math.PI * 20.0/50)));


	}
}
