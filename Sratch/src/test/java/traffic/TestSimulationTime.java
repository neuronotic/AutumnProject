package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.SimulationTime.*;

import org.junit.Rule;
import org.junit.Test;

public class TestSimulationTime {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	@Test
	public void hashCodeOfTwoTimesConstructedWithSameValuesAreEqual() throws Exception {
		assertThat(time(1).hashCode(), equalTo(time(1).hashCode()));
	}

	@Test
	public void twoTimesConstructedWithSameValuesAreEqual() throws Exception {
		assertThat(time(42), equalTo(time(42)));
	}

	@Test
	public void timeReturnsTimeConstructedWith() throws Exception {
		assertThat(CellTime.cellTime(null, time(42)).getTime(), equalTo(time(42)));
	}
}
