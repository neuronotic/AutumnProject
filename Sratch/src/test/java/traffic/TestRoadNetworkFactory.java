package traffic;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static snippet.RoadNetworkFactory.*;
import static snippet.RoadNetworkMatchers.*;

import org.junit.Rule;
import org.junit.Test;

import snippet.Junction;


public class TestRoadNetworkFactory {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Junction inJunction = context.mock(Junction.class, "inJunction");
	private final Junction outJunction = context.mock(Junction.class, "outJunction");

	@Test
	public void junctionCanBeCreated() {
		assertThat(junction(), notNullValue());;
	}

	@Test
	public void tripFromCreateTripOriginAtJunction() {
		assertThat(tripFrom(inJunction), isTripOriginAt(inJunction));
	}

	@Test
	public void junctionConnectedToReturnsJunctionPair() {
		assertThat(adjacent(inJunction, outJunction), isAdjacentPairOfJunctions(inJunction, outJunction));
	}
}
