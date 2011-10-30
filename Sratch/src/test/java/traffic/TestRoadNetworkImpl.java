package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Rule;
import org.junit.Test;

public class TestRoadNetworkImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Segment segment = context.mock(Segment.class);
	private final Junction origin = context.mock(Junction.class, "origin");
	private final Junction destination = context.mock(Junction.class, "destination");

	@Test
	public void shortestRouteInNetworkWithOneSegmentReturnsSegment() throws Exception {
		assertThat(new RoadNetworkImpl(segment).shortestRoute(origin, destination), equalTo(segment));
	}
}