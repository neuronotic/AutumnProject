package traffic;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.RoadNetworkFactory.*;
import static traffic.RoadNetworkMatchers.*;

import org.junit.Rule;
import org.junit.Test;


public class TestRoadNetworkFactory {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Junction inJunction = context.mock(Junction.class, "inJunction");

	private final Segment segment = context.mock(Segment.class);

	@Test
	public void junctionCanBeCreated() {
		assertThat(new JunctionImpl("myName"), isJunctionCalled("myName"));
	}

	@Test
	public void roadNeworkCanBeCreated() {
		assertThat(roadNetwork(segment), notNullValue());
	}

	@Test
	public void tripFromCreateTripOriginAtJunction() {
		assertThat(tripFrom(inJunction), isTripOriginAt(inJunction));
	}
}
