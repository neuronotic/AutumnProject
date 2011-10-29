package traffic;

import static org.hamcrest.MatcherAssert.*;
import static traffic.RoadNetworkMatchers.*;

import org.junit.Rule;
import org.junit.Test;

public class TestAdjacentJunctionPairImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final CellChain cellChain = context.mock(CellChain.class);

	private final Junction inJunction = context.mock(Junction.class, "inJunction");
	private final Junction outJunction = context.mock(Junction.class, "outJunction");

	@Test
	public void testName() throws Exception {
		assertThat(new AdjacentJunctionPairImpl(inJunction, outJunction).connectedByCellChain(cellChain),
				isSegmentBetween(inJunction, outJunction));
	}
}
