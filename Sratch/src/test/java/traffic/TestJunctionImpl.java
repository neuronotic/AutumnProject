package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.RoadNetworkMatchers.*;

import org.jmock.Expectations;
import org.jmock.Sequence;
import org.junit.Rule;
import org.junit.Test;

public class TestJunctionImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Vehicle vehicle0 = context.mock(Vehicle.class, "vehicle0");
	private final Vehicle vehicle1 = context.mock(Vehicle.class, "vehicle1");

	private final Junction junction = new JunctionImpl("myJunction");

	@Test
	public void addedVehiclesArePlacedInOrderOnQueue() throws Exception {
		junction.addVehicle(vehicle0);
		junction.addVehicle(vehicle1);

		final Sequence vehicleStarts = context.sequence("vehicleStarts");
		context.checking(new Expectations() {
			{
				oneOf(vehicle0).startJourney(); inSequence(vehicleStarts);
				oneOf(vehicle1).startJourney(); inSequence(vehicleStarts);
			} //what about vehicle actually entering junction? who cares. vehicle is responsible for that.
		});
		junction.step();
		junction.step();
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
