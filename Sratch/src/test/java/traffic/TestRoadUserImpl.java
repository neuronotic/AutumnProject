package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.RoadUserMatchers.*;

import java.util.Iterator;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;


public class TestRoadUserImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final JourneyHistory history = context.mock(JourneyHistory.class);
	private final Iterator<Cell> itinerary = context.mock(Iterator.class);
	private final Cell cell = context.mock(Cell.class, "cell0");

	@Test
	public void eachStepAdvancesUserToIterator() throws Exception {
		final RoadUser roadUser = new RoadUserImpl(itinerary, history);

		context.checking(new Expectations() {{
			oneOf(itinerary).next(); will(returnValue(cell));
			oneOf(cell).enter(roadUser);
			oneOf(history).stepped();
		}});
		roadUser.step();
		assertThat(roadUser, isLocatedAt(cell));
	}

	@Test
	public void journeyTimeIsReadFromHistory() throws Exception {
		final RoadUser roadUser = new RoadUserImpl(itinerary, history);

		context.checking(new Expectations() {{
			oneOf(history).journeyTime(); will(returnValue(42));
		}});
		assertThat(roadUser.journeyTime(), equalTo(42));
	}
}
