package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestSegmentBuilderImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Junction inJunction = context.mock(Junction.class, "inJunction");
	private final Junction outJunction = context.mock(Junction.class, "outJunction");
	private final SegmentFactory segmentFactory = context.mock(SegmentFactory.class);
	private final CellChainBuilder cellChainBuilder = context.mock(CellChainBuilder.class);
	private final Segment segment = context.mock(Segment.class);

	private final int segmentLength = 5;
	private final String segmentName = "segment0";

	@Test
	public void buildsSegmentObjectWithSuppliedNameInOutJunctionAndLength() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(segmentFactory).createSegment(segmentName, inJunction, cellChainBuilder, outJunction); will(returnValue(segment));
				oneOf(cellChainBuilder).cellChainOfLength(segmentLength); will(returnValue(cellChainBuilder));
			}
		});

		final SegmentBuilder segmentBuilder = new SegmentBuilderImpl(segmentFactory, cellChainBuilder)
			.withName(segmentName)
			.withInJunction(inJunction)
			.withOutJunction(outJunction)
			.withLength(segmentLength);

		assertThat(segmentBuilder.make(), equalTo(segment));
	}

}
