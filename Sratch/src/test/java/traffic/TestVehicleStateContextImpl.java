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
	private final Cell cell1 = context.mock(Cell.class, "cell1");
	private final JourneyHistoryBuilder journeyHistory = context.mock(JourneyHistoryBuilder.class);
	private final Vehicle vehicle = context.mock(Vehicle.class);
	private final NullCellFactory nullCellFactory = context.mock(NullCellFactory.class);
	private final Cell nullCell0 = context.mock(Cell.class, "nullCell0");
	private final JourneyEndedMessageFactory journeyEndedMessageFactory = context.mock(JourneyEndedMessageFactory.class);
	private final JourneyEndedMessage journeyEndedMessage = context.mock(JourneyEndedMessage.class);
	private VehicleStateContext stateContext;

	@Before
	public void setUp() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(nullCellFactory).createNullCell(); will(returnValue(nullCell0));
				ignoring(nullCell0);
			}
		});
		stateContext = new VehicleStateContextImpl(journeyEndedEventBus, journeyEndedMessageFactory, nullCellFactory, asList(cell0, cell1), journeyHistory);
	}


	@Test
	public void journeyEndedCausesCurrentCellToBeReplacedWithNullCellAndMessageSentOnJourneyEndedEventBus() throws Exception {
		currentLocationChangesToCellIfMoveSuccessfullyCallsEnter();
		final Cell nullCell1 = context.mock(Cell.class, "nullCell1");

		context.checking(new Expectations() {
			{
				oneOf(journeyEndedMessageFactory).create(vehicle); will(returnValue(journeyEndedMessage));
				oneOf(journeyEndedEventBus).post(journeyEndedMessage);
				oneOf(cell0).leave(vehicle);
				oneOf(nullCellFactory).createNullCell(); will(returnValue(nullCell1));

			}
		});
		stateContext.journeyEnded(vehicle);
		assertThat(stateContext.location(), is(nullCell1));
	}

	@Test
	public void currentLocationRemainsUnchangedIfMoveUnsuccessfullyCallsEnter() throws Exception {
		currentLocationChangesToCellIfMoveSuccessfullyCallsEnter();

		context.checking(new Expectations() {
			{
				oneOf(cell1).enter(vehicle); will(returnValue(false));
			}
		});
		stateContext.move(vehicle);
		assertThat(stateContext.location(), is(cell0));

	}

	@Test
	public void currentLocationChangesToCellIfMoveSuccessfullyCallsEnter() throws Exception {
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
			oneOf(journeyHistory).journeyTime(); will(returnValue(time(42)));
		}});
		assertThat(stateContext.journeyTime(), equalTo(time(42)));
	}
}
