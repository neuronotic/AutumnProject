package traffic;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestItineraryImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Segment segment = context.mock(Segment.class);
	private final CellChain cellChain = context.mock(CellChain.class);
	private final Junction inJunctionCell = context.mock(Junction.class, "inJunctionCell");
	private final Cell segmentCell1 = context.mock(Cell.class, "segmentCell1");
	private final Cell segmentCell2 = context.mock(Cell.class, "segmentCell2");
	private final Junction outJunctionCell = context.mock(Junction.class, "outJunctionCell");

	@Test
	public void testName() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(segment).inJunction(); will(returnValue(inJunctionCell));
				oneOf(segment).cellChain(); will(returnValue(cellChain));
				oneOf(cellChain).iterator(); will(returnIterator(segmentCell1, segmentCell2));
				oneOf(segment).outJunction(); will(returnValue(outJunctionCell));
			}
		});

		assertThat(new ItineraryImpl(segment), contains(inJunctionCell, segmentCell1, segmentCell2, outJunctionCell));
	}
}
