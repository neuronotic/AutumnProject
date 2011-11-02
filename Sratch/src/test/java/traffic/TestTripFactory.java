package traffic;

import static org.hamcrest.MatcherAssert.*;
import static traffic.RoadNetworkMatchers.*;

import org.junit.Rule;
import org.junit.Test;

public class TestTripFactory {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Junction inJunction = context.mock(Junction.class, "inJunction");

	@Test
	public void tripFromCreateTripOriginAtJunction() {
		assertThat(TripFactory.tripFrom(inJunction), isTripOriginAt(inJunction));
	}
}
