package traffic;

import static java.util.Arrays.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.SimulationTime.*;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class TestVehicleStateContextImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final MyEventBus journeyEndedEventBus = context.mock(MyEventBus.class);
	private final Cell cell0 = context.mock(Cell.class, "cell0");
	private final JourneyHistoryBuilder journeyHistoryBuilder = context.mock(JourneyHistoryBuilder.class);
	private final JourneyHistory journeyHistory = context.mock(JourneyHistory.class);
	private final Vehicle vehicle = context.mock(Vehicle.class);
	private final Cell nullCell0 = context.mock(Cell.class, "nullCell0");
	private final JourneyEndedMessageFactory journeyEndedMessageFactory = context.mock(JourneyEndedMessageFactory.class);
	private final JourneyEndedMessage journeyEndedMessage = context.mock(JourneyEndedMessage.class);
	private VehicleStateContext stateContext;

	@Before
	public void setUp() throws Exception {
		context.checking(new Expectations() {
			{
				ignoring(nullCell0);
			}
		});
		stateContext = new VehicleStateContextImpl(journeyEndedEventBus, journeyEndedMessageFactory, journeyHistoryBuilder, new NullCell(), asList(cell0));
	}

	@Test
	public void currentLocationRemainsUnchangedIfMoveUnsuccessfullyCallsEnter() throws Exception {
		final Cell previousLocation = stateContext.location();
		context.checking(new Expectations() {
			{
				oneOf(cell0).enter(vehicle); will(returnValue(false));
			}
		});
		stateContext.move(vehicle);
		assertThat(stateContext.location(), is(previousLocation));
	}

	@Test
	public void currentLocationChangesToCellIfMoveSuccessfullyCallsEnter() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(cell0).enter(vehicle); will(returnValue(true));
				oneOf(journeyHistoryBuilder).cellEntered(cell0);
			}
		});
		stateContext.move(vehicle);
		assertThat(stateContext.location(), is(cell0));
	}

	@Test
	public void journeyEndedCausesCurrentCellToLeftAndJourneyEndedMessagePostedOnEventBus() throws Exception {
		currentLocationChangesToCellIfMoveSuccessfullyCallsEnter();
		context.checking(new Expectations() {
			{
				oneOf(journeyHistoryBuilder).noteEndTime();
				oneOf(journeyHistoryBuilder).make(vehicle); will(returnValue(journeyHistory));
				oneOf(journeyEndedMessageFactory).create(vehicle, journeyHistory); will(returnValue(journeyEndedMessage));
				oneOf(journeyEndedEventBus).post(journeyEndedMessage);
				oneOf(cell0).leave();
			}
		});
		stateContext.journeyEnded(vehicle);
	}

	@Test
	public void hasNextReturnsTrueOnlyIfCellsRemainToBeVisited() throws Exception {
		assertThat(stateContext.hasJourneyRemaining(), is(true));
	}

	@Test
	public void journeyTimeIsReadFromHistory() throws Exception {
		context.checking(new Expectations() {{
			oneOf(journeyHistoryBuilder).journeyTime(); will(returnValue(time(42)));
		}});
		assertThat(stateContext.journeyTime(), equalTo(time(42)));
	}
}
