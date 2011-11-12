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

	private final CellChainBuilder cellChainBuilder = context.mock(CellChainBuilder.class);
	private final CellChain cellChain = context.mock(CellChain.class);

	@Test
	public void getCellAtIndexReturnsDelgatesCallToCellChain() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(cellChainBuilder).make(with(NetworkMatchers.segmentNamed("mySegment"))); will(returnValue(cellChain));
				oneOf(cellChain).getCellAtIndex(0); will(returnValue(segmentCell0));
				oneOf(cellChain).getCellAtIndex(1); will(returnValue(segmentCell1));
			}
		});
		final Segment segment = new SegmentImpl("mySegment", inJunction, cellChainBuilder, outJunction);
		assertThat(segment.getCell(0), is(segmentCell0));
		assertThat(segment.getCell(1), is(segmentCell1));

	}

	@Test
	public void segmentContainsCellsCorrespondingToInJunctionFollowedByCellsInCellChainFollowedByOutJunction() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(cellChainBuilder).make(with(NetworkMatchers.segmentNamed("mySegment"))); will(returnValue(cellChain));
				oneOf(cellChain).iterator(); will(returnIterator(segmentCell0, segmentCell1));
				oneOf(cellChain).cellCount(); will(returnValue(2));
			}
		});

		final Segment segment = new SegmentImpl("mySegment", inJunction, cellChainBuilder, outJunction);
		assertThat(segment.cells(), contains(inJunction, segmentCell0, segmentCell1, outJunction));
		assertThat(segment, NetworkMatchers.segmentHasLength(2));
	}
}
