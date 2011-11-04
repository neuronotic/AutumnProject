package traffic;

import static java.util.Arrays.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.VehicleMatchers.*;

import java.util.Iterator;

import org.jmock.Expectations;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;


public class TestVehicleImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final JourneyHistory history = context.mock(JourneyHistory.class);
	private final Cell cell = context.mock(Cell.class, "cell0");
	private final Iterator<Cell> itinerary = asList(cell).iterator();
	private final VehicleStateContext stateContext = context.mock(VehicleStateContext.class);
	final Vehicle vehicle = new VehicleImpl("myVehicle", itinerary, history, stateContext);

	@Test
	@Ignore
	public void eachStepAdvancesUserToIterator() throws Exception {
		context.checking(new Expectations() {{
			oneOf(cell).enter(vehicle);
			oneOf(history).stepped();
		}});
		vehicle.step();
		assertThat(vehicle, isLocatedAt(cell));
	}

	@Test
	@Ignore
	public void journeyTimeIsReadFromHistory() throws Exception {
		context.checking(new Expectations() {{
			oneOf(history).journeyTime(); will(returnValue(42));
		}});
		assertThat(vehicle.journeyTime(), equalTo(42));
	}

	@Test
	public void stepMovesVehicleAlongItinerary() throws Exception {
		context.checking(new Expectations() {{
			oneOf(stateContext).nextCellInItinerary(); will(returnValue(cell));
			oneOf(cell).enter(vehicle);
			oneOf(stateContext).setLocation(cell);
			oneOf(stateContext).stepHistory();
			oneOf(stateContext).location(); will(returnValue(cell));
		}});
		vehicle.step();
		//assertThat(vehicle, isLocatedAt(cell));
	}

	@Test
	public void journeyTimeDelegatesRequestToStateContext() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(stateContext).journeyTime(); will(returnValue(42));
			}
		});
		assertThat(vehicle.journeyTime(), equalTo(42));
	}

	@Test
	public void vehicleIsNamed() throws Exception {
		assertThat(vehicle, VehicleMatchers.vehicleIsNamed("myVehicle"));
	}
}
