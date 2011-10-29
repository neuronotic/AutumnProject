package traffic;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import traffic.endtoend.RoadUser;

public class TestRoadUserManager {
	private Itinerary itinerary;

	@Test
	public void roadUserCanBeCreated() throws Exception {
		assertThat(new RoadUserManagerImpl().roadUser(itinerary), notNullValue(RoadUser.class));
	}
}
