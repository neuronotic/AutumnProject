package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.RoadNetworkMatchers.*;

import org.junit.Rule;
import org.junit.Test;

public class TestCellChainFactoryImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Segment segment = context.mock(Segment.class);

	@Test
	public void cellChainHasSpecifiedNumberOfCellsCreated() {
		final CellChain cellChain = new CellChainFactoryImpl(3).make(segment);

		assertThat(cellChain, isCellChainWithCellCount(3));
		assertThat(cellChain, contains(isA(Cell.class), isA(Cell.class), isA(Cell.class)));
	}
}
