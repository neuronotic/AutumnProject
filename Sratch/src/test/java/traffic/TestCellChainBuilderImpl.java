package traffic;

import static java.util.Arrays.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestCellChainBuilderImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final CellFactory cellFactory = context.mock(CellFactory.class);
	private final CellChainFactory cellChainFactory = context.mock(CellChainFactory.class);

	private final Segment segment = context.mock(Segment.class);

	private final Cell cell0 = context.mock(Cell.class, "cell0");
	private final Cell cell1 = context.mock(Cell.class, "cell1");
	private final Cell cell2 = context.mock(Cell.class, "cell2");

	private final CellChain cellChain = context.mock(CellChain.class);

	@Test
	public void cellChainHasSpecifiedNumberOfCellsCreated() {
		context.checking(new Expectations() {
			{
				oneOf(cellFactory).createCell(segment, 0); will(returnValue(cell0));
				oneOf(cellFactory).createCell(segment, 1); will(returnValue(cell1));
				oneOf(cellFactory).createCell(segment, 2); will(returnValue(cell2));

				oneOf(cellChainFactory).createCellChain(asList(cell0, cell1, cell2)); will(returnValue(cellChain));
			}
		});

		assertThat(new CellChainBuilderImpl(cellChainFactory, cellFactory).cellChainOfLength(3).make(segment),
				equalTo(cellChain));
	}
}
