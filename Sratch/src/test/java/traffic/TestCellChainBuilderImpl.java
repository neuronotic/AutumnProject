package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.RoadNetworkMatchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestCellChainBuilderImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final CellFactory cellFactory = context.mock(CellFactory.class);
	private final Segment segment = context.mock(Segment.class);

	private final Cell cell0 = context.mock(Cell.class, "cell0");
	private final Cell cell1 = context.mock(Cell.class, "cell1");
	private final Cell cell2 = context.mock(Cell.class, "cell2");

	@Test
	public void cellChainHasSpecifiedNumberOfCellsCreated() {
		context.checking(new Expectations() {
			{
				oneOf(cellFactory).createCell(segment, 0); will(returnValue(cell0));
				oneOf(cellFactory).createCell(segment, 1); will(returnValue(cell1));
				oneOf(cellFactory).createCell(segment, 2); will(returnValue(cell2));
			}
		});

		final CellChain cellChain = new CellChainBuilderImpl(cellFactory).cellChainOfLength(3).make(segment);

		assertThat(cellChain, isCellChainWithCellCount(3));
		assertThat(cellChain, contains(isA(Cell.class), isA(Cell.class), isA(Cell.class)));
	}
}
