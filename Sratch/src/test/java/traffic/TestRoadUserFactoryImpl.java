package traffic;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;


public class TestRoadUserFactoryImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Itinerary itinerary = context.mock(Itinerary.class);

	@Test
	public void testName() throws Exception {
		context.checking(new Expectations() {
			{
				ignoring(itinerary);
			}
		});
		assertThat(new RoadUserFactoryImpl().createRoadUser(itinerary), notNullValue(RoadUser.class));
	}
}
