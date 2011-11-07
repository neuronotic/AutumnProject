package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.RoadNetworkMatchers.*;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class TestCellImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Vehicle vehicle0 = context.mock(Vehicle.class, "vehicle0");
	private final Vehicle vehicle1 = context.mock(Vehicle.class, "vehicle1");

	private final Segment segment = context.mock(Segment.class);
	private Cell cell;

	@Before
	public void setUp() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(segment).name(); will(returnValue("mySegment"));
			}
		});
		cell = new CellImpl(segment, 0);
	}

	@Test
	public void cellCanOnlyHaveOneOccupantAtATime() throws Exception {
		assertThat(cell.enter(vehicle0), is(true));
		assertThat(cell.enter(vehicle1), is(false));
	}

	@Test
	public void cellNameIsNameOfSegmentAndCellIndex() throws Exception {
		assertThat(cell, cellNamed("mySegment[0]"));
	}
}
