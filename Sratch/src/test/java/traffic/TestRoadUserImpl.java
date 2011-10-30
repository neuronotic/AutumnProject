package traffic;

import static org.hamcrest.MatcherAssert.*;
import static traffic.RoadNetworkMatchers.*;
import static traffic.RoadUserMatchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

import traffic.endtoend.RoadUser;

public class TestRoadUserImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Itinerary itinerary = context.mock(Itinerary.class);
	private final Cell cell0 = context.mock(Cell.class, "cell0");
	private final Cell cell1 = context.mock(Cell.class, "cell1");
	private final Cell cell2 = context.mock(Cell.class, "cell2");

	@Test
	public void eachStepAdvancesUserToIterator() throws Exception {
		context.checking(new Expectations() {{
			oneOf(itinerary).iterator(); will(returnIterator(cell0, cell1, cell2));
		}});

		final RoadUser roadUser = new RoadUserImpl(itinerary);
		context.checking(new Expectations() {{
			oneOf(cell0).enter(roadUser);
		}});
		roadUser.step();

		assertThat(roadUser, isLocatedAt(cell0));
		assertThat(roadUser, hasJourneyTime(1));

		context.checking(new Expectations() {{
			oneOf(cell1).enter(roadUser);
		}});
		roadUser.step();

		assertThat(roadUser, isLocatedAt(cell1));
		assertThat(roadUser, hasJourneyTime(2));

		context.checking(new Expectations() {{
			oneOf(cell2).enter(roadUser);
		}});
		roadUser.step();

		assertThat(roadUser, isLocatedAt(cell2));
		assertThat(roadUser, hasJourneyTime(3));
	}
}
