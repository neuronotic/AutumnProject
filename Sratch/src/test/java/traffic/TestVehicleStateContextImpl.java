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

	private final Cell cell = context.mock(Cell.class);
	private final Iterator<Cell> remainingItinerary = asList(cell).iterator();
	private final JourneyHistory journeyHistory = context.mock(JourneyHistory.class);
	private final VehicleStateContext stateContext = new VehicleStateContextImpl(remainingItinerary, journeyHistory);

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
		assertThat(stateContext.nextCellInItinerary(), is(cell));
	}

	@Test
	public void locationIsPreviouslySetLocation() throws Exception {
		stateContext.setLocation(cell);
		assertThat(stateContext.location(), is(cell));
	}
}
