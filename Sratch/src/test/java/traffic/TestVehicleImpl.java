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
	public void addedListenersAreNotifiedWhenJourneyEndedMessageReceived() throws Exception {
		final Vehicle vehicle = new VehicleImpl("myVehicle", itinerary, history);
		final JourneyEndListener journeyEndListener0 = context.mock(JourneyEndListener.class, "listener0");
		final JourneyEndListener journeyEndListener1 = context.mock(JourneyEndListener.class, "listener1");

		vehicle.addJourneyEndListener(journeyEndListener0);
		vehicle.addJourneyEndListener(journeyEndListener1);

		context.checking(new Expectations() {
			{
				oneOf(journeyEndListener0).notifyOfJourneyEnd(vehicle);
				oneOf(journeyEndListener1).notifyOfJourneyEnd(vehicle);
			}
		});

		vehicle.journeyEnded();
	}

	@Test
	public void eachStepAdvancesUserToIterator() throws Exception {
		final Vehicle vehicle = new VehicleImpl("myVehicle", itinerary, history);

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
		final Vehicle vehicle = new VehicleImpl("myVehicle", itinerary, history);

		context.checking(new Expectations() {{
			oneOf(history).journeyTime(); will(returnValue(42));
		}});
		assertThat(vehicle.journeyTime(), equalTo(42));
	}

	@Test
	public void vehicleIsNamed() throws Exception {
		final Vehicle vehicle = new VehicleImpl("myVehicle", itinerary, null);
		assertThat(vehicle, VehicleMatchers.vehicleIsNamed("myVehicle"));
	}
}
