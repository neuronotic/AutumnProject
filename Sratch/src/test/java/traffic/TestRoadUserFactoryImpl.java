package traffic;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import traffic.endtoend.RoadUser;

public class TestRoadUserFactoryImpl {
	private Itinerary itinerary;

	@Test
	public void testName() throws Exception {
		assertThat(new RoadUserFactoryImpl().createRoadUser(itinerary), notNullValue(RoadUser.class));
	}
}
