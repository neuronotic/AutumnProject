package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.SimulationTime.*;

import org.junit.Rule;
import org.junit.Test;

public class TestCellTime {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Cell cell0 = context.mock(Cell.class);

	@Test
	public void hashCodeOfTwoCellTimesConstructedWithSameValuesAreEqual() throws Exception {
		assertThat(CellTime.cellTime(cell0, time(1)).hashCode(), equalTo(CellTime.cellTime(cell0, time(1)).hashCode()));
	}


	@Test
	public void twoCellTimesWithSameCellAndTimeAreEqual() throws Exception {
		assertThat(CellTime.cellTime(cell0, time(1)), equalTo(CellTime.cellTime(cell0, time(1))));
	}

	@Test
	public void cellReturnsCellConstructedWith() throws Exception {
		assertThat(CellTime.cellTime(cell0, time(42)).getCell(), equalTo(cell0));
	}

	@Test
	public void timeReturnsTimeConstructedWith() throws Exception {
		assertThat(CellTime.cellTime(null, time(42)).getTime(), equalTo(time(42)));
	}
}
