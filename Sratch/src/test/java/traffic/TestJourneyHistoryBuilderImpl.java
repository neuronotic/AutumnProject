package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.CellTime.*;
import static traffic.SimulationMatchers.*;
import static traffic.SimulationTime.*;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class TestJourneyHistoryBuilderImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final TimeKeeper timeKeeper = context.mock(TimeKeeper.class);
	private final SimulationTime initialisedStartTime = time(1);
	private final SimulationTime startTime = time(2);
	private final SimulationTime endTime = time(42);

	private final Cell cell0 = context.mock(Cell.class, "cell0");
	private final Cell cell1 = context.mock(Cell.class, "cell1");
	private final Vehicle vehicle = context.mock(Vehicle.class);
	private final JourneyHistoryFactory journeyHistoryFactory = context.mock(JourneyHistoryFactory.class);
	private final JourneyHistory journeyHistory = context.mock(JourneyHistory.class);
	private JourneyHistoryBuilder journeyHistoryBuilder;

	@Before
	public void setUp() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(timeKeeper).currentTime(); will(returnValue(initialisedStartTime));
			}
		});
		journeyHistoryBuilder = new JourneyHistoryBuilderImpl(journeyHistoryFactory, timeKeeper);
	}

	@Test
	public void makeReturnsResultOfCallToJourneyHistoryFactory() throws Exception {
		journeyHistoryBuilder
			.withStartTime(startTime)
			.withEndTime(endTime)
			.withVehicle(vehicle);

		context.checking(new Expectations() {
			{
				oneOf(journeyHistoryFactory).create(vehicle, startTime, journeyHistoryBuilder.cellEntryTimes(), endTime); will(returnValue(journeyHistory));
			}
		});

		assertThat(journeyHistoryBuilder.make(), is(journeyHistory));
	}

	@Test
	public void withVehicleSetsVehicleReturnedByVehicle() throws Exception {
		assertThat(journeyHistoryBuilder.withVehicle(vehicle), is(journeyHistoryBuilder));
		assertThat(journeyHistoryBuilder.vehicle(), is(vehicle));
	}

	@Test
	public void withEndTimeSetsTimeReturnedByEndTime() throws Exception {
		assertThat(journeyHistoryBuilder.withEndTime(endTime), is(journeyHistoryBuilder));
		assertThat(journeyHistoryBuilder.endTime(), isTime(endTime));
	}

	@Test
	public void NoteEndTimeCalledSetsTimeReturnedByEndTimeAsTimeKeepersCurrentTime() throws Exception {
		final SimulationTime endTime = time(10);
		context.checking(new Expectations() {
			{
				oneOf(timeKeeper).currentTime(); will(returnValue(endTime));
			}
		});
		journeyHistoryBuilder.noteEndTime();

		assertThat(journeyHistoryBuilder.endTime(), isTime(endTime));
	}

	@Test
	public void cellEntryTimesReturnsListOfCellTimeObjectsCorrespondingToEachTimeCellEnteredAndCellEntryTimeCalled() throws Exception {
		context.checking(new Expectations() {
			{
				exactly(2).of(timeKeeper).currentTime();
					will(onConsecutiveCalls(
							returnValue(time(2)),
							returnValue(time(5))));
			}
		});
		journeyHistoryBuilder.cellEntered(cell0);
		journeyHistoryBuilder.cellEntered(cell1);
		assertThat(journeyHistoryBuilder.cellEntryTimes(), containsInAnyOrder(cellTime(cell0, time(2)), cellTime(cell1, time(5))));

		journeyHistoryBuilder.withCellEntryTime(cell0, time(7));
		assertThat(journeyHistoryBuilder.cellEntryTimes(), containsInAnyOrder(cellTime(cell0, time(2)), cellTime(cell1, time(5)), cellTime(cell0, time(7))));
	}

	@Test
	public void withStartTimeChangesStartTime() throws Exception {
		assertThat(journeyHistoryBuilder.withStartTime(startTime), is(journeyHistoryBuilder));
		assertThat(journeyHistoryBuilder.startTime(), isTime(startTime));
	}

	@Test
	public void startTimeInitialisedToTimeKeepersCurrentTimeOnConstruction() throws Exception {
		assertThat(journeyHistoryBuilder.startTime(), isTime(initialisedStartTime));
	}
}
