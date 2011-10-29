package traffic;

import static org.hamcrest.MatcherAssert.*;
import static traffic.RoadUserMatchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

import traffic.endtoend.RoadUser;

public class TestRoadUserImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Itinerary itinerary = context.mock(Itinerary.class);

	private final RoadUser roadUser = new RoadUserImpl(itinerary);

	private final Cell originCell = context.mock(Cell.class);

	@Test
	public void firstStepLocatesUserAtOriginJunction() throws Exception {
		context.checking(new Expectations() {{
			oneOf(itinerary).iterator(); will(returnIterator(originCell));
			oneOf(originCell).enter(roadUser);
		}});

		roadUser.step();

		assertThat(roadUser, isLocatedAt(originCell));
	}
}
