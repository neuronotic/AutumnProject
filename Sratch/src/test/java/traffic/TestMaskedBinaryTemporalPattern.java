package traffic;

import static java.util.Arrays.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.SimulationTime.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestMaskedBinaryTemporalPattern {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final TimeKeeper timeKeeper = context.mock(TimeKeeper.class);
	private final Integer[] mask = new Integer[] {0,1,1};
	private final MaskedBinaryTemporalPattern maskedTemporalPattern = new MaskedBinaryTemporalPattern(timeKeeper, asList(mask));

	@Test
	public void modifierMatchesCorrectValueInMaskWithPeriod3() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(timeKeeper); will(returnValue(time(4)));
			}
		});
		assertThat(maskedTemporalPattern.modifier(), is(1.0));

		context.checking(new Expectations() {
			{
				oneOf(timeKeeper); will(returnValue(time(9)));
			}
		});
		assertThat(maskedTemporalPattern.modifier(), is(0.0));

	}

}
