package traffic;

import static java.util.Arrays.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Iterator;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestVehicleStateContextImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Cell cell0 = context.mock(Cell.class, "cell0");
	private final Cell cell1 = context.mock(Cell.class, "cell1");
	private final Iterator<Cell> remainingItinerary = asList(cell0, cell1).iterator();
	private final JourneyHistory journeyHistory = context.mock(JourneyHistory.class);
	private final Vehicle vehicle = context.mock(Vehicle.class);
	private VehicleStateContext stateContext = new VehicleStateContextImpl(remainingItinerary, journeyHistory);

	@Test
	public void hasNextDelegatesQueryToIterator() throws Exception {
		final Iterator<Cell> mockRemainingItinerary = context.mock(Iterator.class);
		stateContext = new VehicleStateContextImpl(mockRemainingItinerary, journeyHistory);

		context.checking(new Expectations() {
			{
				oneOf(mockRemainingItinerary).hasNext(); will(returnValue(true));
			}
		});
		assertThat(stateContext.hasNext(), is(true));

		context.checking(new Expectations() {
			{
				oneOf(mockRemainingItinerary).hasNext(); will(returnValue(false));
			}
		});
		assertThat(stateContext.hasNext(), is(false));
	}

	@Test
	public void journeyTimeIsReadFromHistory() throws Exception {
		context.checking(new Expectations() {{
			oneOf(journeyHistory).journeyTime(); will(returnValue(42));
		}});
		assertThat(stateContext.journeyTime(), equalTo(42));
	}

	@Test
	public void stepHistoryCallsSteppedOnJourneyHistoryInstantiatedWith() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(journeyHistory).stepped();
			}
		});
		stateContext.stepHistory();
	}

	@Test
	public void nextCellInItineraryReturnsNextInSuppliedRemainingItinerary() throws Exception {
		assertThat(stateContext.nextCellInItinerary(), is(cell0));
		assertThat(stateContext.nextCellInItinerary(), is(cell1));
	}

	@Test
	public void locationIsPreviouslySetLocation() throws Exception {
		stateContext.setLocation(cell0);
		assertThat(stateContext.location(), is(cell0));
	}
}
