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

	private final LinkOccupancy linkOccupancy = context.mock(LinkOccupancy.class);
	private final LinkOccupancyFactory linkOccupancyFactory = context.mock(LinkOccupancyFactory.class);
	private final OccupancyFactory occupancyFactory = context.mock(OccupancyFactory.class);
	private final Occupancy occupancyMeasure = context.mock(Occupancy.class);

	private final int occupancy = 2;
	private final int capacity = 5;

	private final Link link = linkWithConstructorExpectations();

	@Test
	public void congestionReturnsValueOfOccupancyDividedByCapacity() throws Exception {
		defineExpectationsForOccupancyOnCellChain();
		defineExpectationsForCapacityOnCellChain();
		assertThat(link.congestion(), is(0.4));
	}

	@Test
	public void occupancyReturnsLinkOccupancyObject() throws Exception {
		defineExpectationsForOccupancyOnCellChain();
		defineExpectationsForCapacityOnCellChain();

		context.checking(new Expectations() {
			{
				oneOf(occupancyFactory).create(occupancy, capacity); will(returnValue(occupancyMeasure));
				oneOf(linkOccupancyFactory).create(link, occupancyMeasure); will(returnValue(linkOccupancy));
			}
		});
		assertThat(link.occupancy(), is(linkOccupancy));
	}

	@Test
	public void getCellAtIndexReturnsDelgatesCallToCellChain() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(cellChain).getCellAtIndex(0); will(returnValue(linkCell0));
				oneOf(cellChain).getCellAtIndex(1); will(returnValue(linkCell1));
			}
		});
		assertThat(link.getCell(0), is(linkCell0));
		assertThat(link.getCell(1), is(linkCell1));

	}

	@Test
	public void linkHasLength2() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(cellChain).cellCount(); will(returnValue(2));
			}
		});
		assertThat(link, NetworkMatchers.linkHasLength(2));
	}

	@Test
	public void linkContainsCellsCorrespondingToInJunctionFollowedByCellsInCellChainFollowedByOutJunction() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(cellChain).iterator(); will(returnIterator(linkCell0, linkCell1));
			}
		});
		assertThat(link.cells(), contains(inJunction, linkCell0, linkCell1, outJunction));
	}

	private LinkImpl linkWithConstructorExpectations() {
		context.checking(new Expectations() {
			{
				oneOf(cellChainBuilder).make(with(NetworkMatchers.linkNamed("myLink"))); will(returnValue(cellChain));
			}
		});
		return new LinkImpl(linkOccupancyFactory, occupancyFactory, "myLink", inJunction, cellChainBuilder, outJunction);
	}


	private void defineExpectationsForOccupancyOnCellChain() {
		context.checking(new Expectations() {
			{
				oneOf(cellChain).occupancy(); will(returnValue(occupancy));
			}
		});
	}

	private void defineExpectationsForCapacityOnCellChain() {
		context.checking(new Expectations() {
			{
				oneOf(cellChain).cellCount(); will(returnValue(capacity));
			}
		});
	}

}
