package traffic;

import static java.util.Arrays.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestVehicleStateContextImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Cell cell0 = context.mock(Cell.class, "cell0");
	private final JourneyHistory journeyHistory = context.mock(JourneyHistory.class);
	private final Vehicle vehicle = context.mock(Vehicle.class);
	private final VehicleStateContext stateContext = new VehicleStateContextImpl(asList(cell0), journeyHistory);

	@Test
	public void moveCausesCurrentLocationToBecomeNextCellInRemainingItinerary() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(cell0).enter(vehicle); will(returnValue(true));
				oneOf(journeyHistory).stepped();
			}
		});

		stateContext.move(vehicle);
		assertThat(stateContext.location(), is(cell0));
	}

	@Test
	public void hasNextReturnsTrueOnlyIfCellsRemainToBeVisited() throws Exception {
		assertThat(stateContext.hasJourneyRemaining(), is(true));
	}

	@Test
	public void journeyTimeIsReadFromHistory() throws Exception {
		context.checking(new Expectations() {{
			oneOf(journeyHistory).journeyTime(); will(returnValue(42));
		}});
		assertThat(stateContext.journeyTime(), equalTo(42));
	}
}
