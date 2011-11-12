package traffic;

import static org.hamcrest.MatcherAssert.*;
import static traffic.NetworkMatchers.*;

import org.junit.Rule;
import org.junit.Test;

public class TestTripOrigin {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Junction originJunction = context.mock(Junction.class, "originJunction");
	private final Junction destinationJunction = context.mock(Junction.class, "destinationJuntion");

	@Test
	public void passingDestinationToOriginCreatesTrip() throws Exception {
		assertThat(new TripOriginImpl(originJunction).to(destinationJunction), isTripBetween(originJunction, destinationJunction));
	}
}
