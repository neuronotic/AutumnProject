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
	private final SimulationTime startTime = time(1);
	private final Cell cell0 = context.mock(Cell.class, "cell0");
	private final Cell cell1 = context.mock(Cell.class, "cell1");

	private JourneyHistoryBuilder journeyHistoryBuilder;

	@Before
	public void setUp() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(timeKeeper).currentTime(); will(returnValue(startTime));
			}
		});
		journeyHistoryBuilder = new JourneyHistoryBuilderImpl(timeKeeper);
	}

	@Test
	public void endTimeReturnsValueCorrespondingToWhenNoteEndTimeCalled() throws Exception {
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
	public void cellEntryTimesReturnsListOfCellTimeObjectsCorrespondingToEachTimeCellEnteredCalled() throws Exception {
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

	}

	@Test
	public void startTimeInitialisedToTimeKeepersCurrentTimeOnConstruction() throws Exception {
		assertThat(journeyHistoryBuilder.startTime(), isTime(startTime));
	}
}
