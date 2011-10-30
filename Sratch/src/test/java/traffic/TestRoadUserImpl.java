package traffic;

import static org.hamcrest.MatcherAssert.*;
import static traffic.RoadNetworkMatchers.*;
import static traffic.RoadUserMatchers.*;

import java.util.Iterator;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;


public class TestRoadUserImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Iterator<Cell> itinerary = context.mock(Iterator.class);
	private final Cell cell0 = context.mock(Cell.class, "cell0");
	private final Cell cell1 = context.mock(Cell.class, "cell1");

	@Test
	public void eachStepAdvancesUserToIterator() throws Exception {
		final RoadUser roadUser = new RoadUserImpl(itinerary);

		context.checking(new Expectations() {{
			oneOf(itinerary).next(); will(returnValue(cell0));
			oneOf(cell0).enter(roadUser);
		}});
		roadUser.step();
		assertThat(roadUser, isLocatedAt(cell0));
		assertThat(roadUser, hasJourneyTime(1));

		context.checking(new Expectations() {{
			oneOf(itinerary).next(); will(returnValue(cell1));
			oneOf(cell1).enter(roadUser);
		}});
		roadUser.step();

		assertThat(roadUser, isLocatedAt(cell1));
		assertThat(roadUser, hasJourneyTime(2));
	}
}
