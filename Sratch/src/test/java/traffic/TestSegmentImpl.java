package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestSegmentImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();


	private final Junction inJunction = context.mock(Junction.class, "inJunction");
	private final Junction outJunction = context.mock(Junction.class, "outJunction");

	private final Cell segmentCell0 = context.mock(Cell.class, "segmentCell0");
	private final Cell segmentCell1 = context.mock(Cell.class, "segmentCell1");

	private final CellChain cellChain0 = context.mock(CellChain.class, "cellChain0");

	@Test
	public void segmentContainsCellsCorrespondingToInJunctionFollowedByCellsInCellChainFollowedByOutJunction() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(cellChain0).iterator(); will(returnIterator(segmentCell0, segmentCell1));
			}
		});

		final Segment segment = new SegmentImpl(inJunction, cellChain0, outJunction);
		assertThat(segment.cells(), contains(inJunction, segmentCell0, segmentCell1, outJunction));
	}
}
