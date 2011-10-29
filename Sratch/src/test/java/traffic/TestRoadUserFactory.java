package traffic;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static traffic.RoadUserFactory.*;

import org.junit.Test;

import traffic.Itinerary;

public class TestRoadUserFactory {
	private final Itinerary itinerary = null;

	@Test
	public void roadUserCanBeCreated() throws Exception {
		assertThat(roadUser(itinerary), notNullValue());
	}
}
