package traffic;

import static org.hamcrest.MatcherAssert.*;
import static traffic.RoadNetworkMatchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestCellImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	@Test
	public void cellNameIsNameOfSegmentAndCellIndex() throws Exception {
		final Segment segment = context.mock(Segment.class);
		context.checking(new Expectations() {
			{
				oneOf(segment).name(); will(returnValue("mySegment"));
			}
		});
		assertThat(new CellImpl(segment, 0), cellNamed("mySegment[0]"));
	}
}
