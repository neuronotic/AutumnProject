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
	private final Iterator<Cell> remainingItinerary = asList(cell0).iterator();
	private final JourneyHistory journeyHistory = context.mock(JourneyHistory.class);
	private final Vehicle vehicle = context.mock(Vehicle.class);
	private VehicleStateContext stateContext = new VehicleStateContextImpl(remainingItinerary, journeyHistory);

	@Test
	public void moveCausesCurrentLocationToBecomeNextCellInRemainingItinerary() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(cell0).enter(vehicle);
				oneOf(journeyHistory).stepped();
			}
		});

		stateContext.move(vehicle);
		assertThat(stateContext.location(), is(cell0));
	}

	@Test
	public void hasNextDelegatesQueryToIterator() throws Exception {
		final Iterator<Cell> mockRemainingItinerary = context.mock(Iterator.class);
		stateContext = new VehicleStateContextImpl(mockRemainingItinerary, journeyHistory);

		context.checking(new Expectations() {
			{
				oneOf(mockRemainingItinerary).hasNext(); will(returnValue(true));
			}
		});
		assertThat(stateContext.hasJourneyRemaining(), is(true));

		context.checking(new Expectations() {
			{
				oneOf(mockRemainingItinerary).hasNext(); will(returnValue(false));
			}
		});
		assertThat(stateContext.hasJourneyRemaining(), is(false));
	}

	@Test
	public void journeyTimeIsReadFromHistory() throws Exception {
		context.checking(new Expectations() {{
			oneOf(journeyHistory).journeyTime(); will(returnValue(42));
		}});
		assertThat(stateContext.journeyTime(), equalTo(42));
	}
}
