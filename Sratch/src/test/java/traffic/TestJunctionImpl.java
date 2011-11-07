package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.RoadNetworkMatchers.*;

import org.junit.Rule;
import org.junit.Test;

public class TestJunctionImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Vehicle vehicle0 = context.mock(Vehicle.class, "vehicle0");
	private final Vehicle vehicle1 = context.mock(Vehicle.class, "vehicle1");

	private final Junction junction = new JunctionImpl("myJunction");

	@Test
	public void leaveEmptiesJunction() throws Exception {
		junction.enter(vehicle0);
		assertThat(junction.isOccupied(), is(true));
		junction.leave(vehicle0);
		assertThat(junction.isOccupied(), is(false));
	}

	@Test
	public void isOccupiedReturnsTrueOnlyIfVehicleIsInCell() throws Exception {
		assertThat(junction.isOccupied(), is(false));
		junction.enter(vehicle0);
		assertThat(junction.isOccupied(), is(true));
	}

	@Test
	public void junctionCanOnlyHaveOneOccupantAtATime() throws Exception {
		assertThat(junction.enter(vehicle0), is(true));
		assertThat(junction.enter(vehicle1), is(false));
	}

	@Test
	public void junctionCanBeCreated() {
		assertThat(junction, isJunctionCalled("myJunction"));
	}
}
