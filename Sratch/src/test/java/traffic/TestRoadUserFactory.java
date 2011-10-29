package traffic;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static traffic.endtoend.RoadUserFactory.*;

import org.junit.Test;

import traffic.endtoend.Itinerary;

public class TestRoadUserFactory {
	private final Itinerary itinerary = null;

	@Test
	public void roadUserCanBeCreated() throws Exception {
		assertThat(roadUser(itinerary), notNullValue());
	}
}
