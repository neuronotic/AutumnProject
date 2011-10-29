package traffic;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

import traffic.endtoend.RoadUser;

public class TestRoadUserManager {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Itinerary itinerary = context.mock(Itinerary.class);
	private final RoadUserFactory roadUserFactory = context.mock(RoadUserFactory.class);

	private final RoadUser roadUser = context.mock(RoadUser.class);

	@Test
	public void roadUserCanBeCreated() throws Exception {
		context.checking(new Expectations() {{
			oneOf(roadUserFactory).createRoadUser(itinerary); will(returnValue(roadUser));
		}});

		assertThat(new RoadUserManagerImpl(roadUserFactory).roadUser(itinerary), notNullValue(RoadUser.class));
	}
}
