package traffic;

import static org.hamcrest.MatcherAssert.*;
import static traffic.RoadNetworkMatchers.*;

import org.junit.Test;



public class TestJunctionImpl {
	@Test
	public void junctionCanBeCreated() {
		assertThat(new JunctionImpl("myName"), isJunctionCalled("myName"));
	}
}
