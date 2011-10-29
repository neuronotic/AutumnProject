package traffic;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static traffic.RoadUserManagerFactory.*;

import org.junit.Test;

public class TestRoadUserManagerFactory {
	@Test
	public void roadUserManagerCanBeCreated() throws Exception {
		assertThat(roadUserManager(), notNullValue(RoadUserManager.class));
	}
}
