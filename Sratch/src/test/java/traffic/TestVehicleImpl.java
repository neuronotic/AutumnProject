package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.VehicleMatchers.*;

import java.util.Iterator;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;


public class TestVehicleImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final JourneyHistory history = context.mock(JourneyHistory.class);
	private final Iterator<Cell> itinerary = context.mock(Iterator.class);
	private final Cell cell = context.mock(Cell.class, "cell0");

	@Test
	public void eachStepAdvancesUserToIterator() throws Exception {
		final Vehicle vehicle = new VehicleImpl(itinerary, history);

		context.checking(new Expectations() {{
			oneOf(itinerary).next(); will(returnValue(cell));
			oneOf(cell).enter(vehicle);
			oneOf(history).stepped();
		}});
		vehicle.step();
		assertThat(vehicle, isLocatedAt(cell));
	}

	@Test
	public void journeyTimeIsReadFromHistory() throws Exception {
		final Vehicle vehicle = new VehicleImpl(itinerary, history);

		context.checking(new Expectations() {{
			oneOf(history).journeyTime(); will(returnValue(42));
		}});
		assertThat(vehicle.journeyTime(), equalTo(42));
	}
}