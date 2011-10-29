package traffic;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.RoadNetworkFactory.*;
import static traffic.RoadNetworkMatchers.*;

import org.junit.Rule;
import org.junit.Test;

import traffic.Junction;
import traffic.Segment;


public class TestRoadNetworkFactory {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Junction inJunction = context.mock(Junction.class, "inJunction");
	private final Junction outJunction = context.mock(Junction.class, "outJunction");

	private final Segment segment = context.mock(Segment.class);

	@Test
	public void junctionCanBeCreated() {
		assertThat(junction(), notNullValue());;
	}

	@Test
	public void roadNeworkCanBeCreated() {
		assertThat(roadNetwork(segment), notNullValue());
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
