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
	private final MyEventBus eventBus = context.mock(MyEventBus.class);
	private final JunctionMeasuresMessageFactory messageFactory = context.mock(JunctionMeasuresMessageFactory.class);

	private final Junction junction = new JunctionImpl(eventBus, messageFactory, "myJunction");

	@Test
	public void junctionCapacity() throws Exception {

	}

	@Test
	public void addedVehiclesArePlacedInOrderOnQueue() throws Exception {
		junction.addVehicle(vehicle0);
		junction.addVehicle(vehicle1);

		assertThat(junction.enter(vehicle1), is(false));
		assertThat(junction.enter(vehicle0), is(true));
		junction.leave();
		assertThat(junction.enter(vehicle1), is(true));
	}

	@Test
	public void isOccupiedReturnsTrueOnlyIfVehicleIsInCell() throws Exception {
		assertThat(junction.isOccupied(), is(false));
		assertThat(junction.enter(vehicle0), is(true));
		assertThat(junction.isOccupied(), is(true));
	}

	@Test
	public void junctionCanOnlyHaveOneOccupantAtATime() throws Exception {
		assertThat(junction.enter(vehicle0), is(true));
		assertThat(junction.enter(vehicle1), is(false));
	}

	@Test
	public void leaveEmptiesJunction() throws Exception {
		junction.enter(vehicle0);
		assertThat(junction.isOccupied(), is(true));
		junction.leave();
		assertThat(junction.isOccupied(), is(false));
	}

	@Test
	public void junctionIsCreatedWithGivenName() {
		assertThat(junction, isJunctionCalled("myJunction"));
	}
}
