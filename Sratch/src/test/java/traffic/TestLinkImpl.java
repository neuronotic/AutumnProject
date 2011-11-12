package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestLinkImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();


	private final Junction inJunction = context.mock(Junction.class, "inJunction");
	private final Junction outJunction = context.mock(Junction.class, "outJunction");

	private final Cell linkCell0 = context.mock(Cell.class, "linkCell0");
	private final Cell linkCell1 = context.mock(Cell.class, "linkCell1");

	private final CellChainBuilder cellChainBuilder = context.mock(CellChainBuilder.class);
	private final CellChain cellChain = context.mock(CellChain.class);

	@Test
	public void getCellAtIndexReturnsDelgatesCallToCellChain() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(cellChainBuilder).make(with(NetworkMatchers.linkNamed("myLink"))); will(returnValue(cellChain));
				oneOf(cellChain).getCellAtIndex(0); will(returnValue(linkCell0));
				oneOf(cellChain).getCellAtIndex(1); will(returnValue(linkCell1));
			}
		});
		final Link link = new LinkImpl("myLink", inJunction, cellChainBuilder, outJunction);
		assertThat(link.getCell(0), is(linkCell0));
		assertThat(link.getCell(1), is(linkCell1));

	}

	@Test
	public void linkContainsCellsCorrespondingToInJunctionFollowedByCellsInCellChainFollowedByOutJunction() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(cellChainBuilder).make(with(NetworkMatchers.linkNamed("myLink"))); will(returnValue(cellChain));
				oneOf(cellChain).iterator(); will(returnIterator(linkCell0, linkCell1));
				oneOf(cellChain).cellCount(); will(returnValue(2));
			}
		});

		final Link link = new LinkImpl("myLink", inJunction, cellChainBuilder, outJunction);
		assertThat(link.cells(), contains(inJunction, linkCell0, linkCell1, outJunction));
		assertThat(link, NetworkMatchers.linkHasLength(2));
	}
}
