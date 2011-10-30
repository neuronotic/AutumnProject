package traffic;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.collect.Iterators;

public class TestItineraryImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Segment segment0 = context.mock(Segment.class, "segment0");
	private final Segment segment1 = context.mock(Segment.class, "segment1");

	private final CellChain cellChain0 = context.mock(CellChain.class, "cellChain0");
	private final CellChain cellChain1 = context.mock(CellChain.class, "cellChain1");

	private final Junction junctionCell0 = context.mock(Junction.class, "junctionCell0");
	private final Junction junctionCell1 = context.mock(Junction.class, "junctionCell1");
	private final Junction junctionCell2 = context.mock(Junction.class, "junctionCell2");

	private final Cell segmentCell0 = context.mock(Cell.class, "segmentCell0");
	private final Cell segmentCell1 = context.mock(Cell.class, "segmentCell1");
	private final Cell segmentCell2 = context.mock(Cell.class, "segmentCell2");

	@Test
	public void itineraryContainsCellsFromRouteJunctionsAndSegmentsInOrderForMultipleSegments() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(segment0).inJunction(); will(returnValue(junctionCell0));
				oneOf(segment0).cellChain(); will(returnValue(cellChain0));
				oneOf(cellChain0).iterator(); will(returnIterator(segmentCell0, segmentCell1));

				oneOf(segment1).inJunction(); will(returnValue(junctionCell1));
				oneOf(segment1).cellChain(); will(returnValue(cellChain1));
				oneOf(cellChain1).iterator(); will(returnIterator(segmentCell2));
				oneOf(segment1).outJunction(); will(returnValue(junctionCell2));
			}
		});

		final ItineraryImpl itineraryImpl = new ItineraryImpl(segment0, segment1);
		final List<Cell> cells = new ArrayList<Cell>();
		Iterators.addAll(cells, itineraryImpl.iterator());
		assertThat(cells, contains(junctionCell0, segmentCell0, segmentCell1, junctionCell1, segmentCell2, junctionCell2));
	}

	@Test
	public void itineraryContainsCellsFromRouteJunctionsAndSegmentsInOrder() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(segment0).inJunction(); will(returnValue(junctionCell0));
				oneOf(segment0).cellChain(); will(returnValue(cellChain0));
				oneOf(cellChain0).iterator(); will(returnIterator(segmentCell1, segmentCell2));
				oneOf(segment0).outJunction(); will(returnValue(junctionCell1));
			}
		});

		assertThat(new ItineraryImpl(segment0), contains(junctionCell0, segmentCell1, segmentCell2, junctionCell1));
	}
}
