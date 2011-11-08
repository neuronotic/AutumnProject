package traffic;

import static java.util.Arrays.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.SimulationTime.*;

import java.util.List;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestJourneyHistoryBuilderImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final JourneyHistoryFactory journeyHistoryFactory = context.mock(JourneyHistoryFactory.class);
	private final JourneyHistory journeyHistory = context.mock(JourneyHistory.class);
	private final Vehicle vehicle = context.mock(Vehicle.class);
	private final SimulationTime startTime = SimulationTime.time(1);
	private final SimulationTime finishTime = SimulationTime.time(10);
	private final Cell cell0 = context.mock(Cell.class, "cell0");
	private final Cell cell1 = context.mock(Cell.class, "cell1");
	private final List<CellTime> cellTimes = asList(CellTime.cellTime(cell0, time(3)), CellTime.cellTime(cell1,time(5)));

	@Test
	public void makeReturnsJourneyHistoryObjectCreatedWithCorrectReferences() throws Exception {
		final JourneyHistoryBuilder journeyHistoryBuilder =
				new JourneyHistoryBuilderImpl(journeyHistoryFactory)
					.withVehicle(vehicle)
					.withStartTime(time(1))
					.withCellEntryTime(cell0, time(3))
					.withCellEntryTime(cell1, time(5))
					.withFinishTime(time(10));

		context.checking(new Expectations() {
			{
				oneOf(journeyHistoryFactory).create(vehicle, startTime, cellTimes, finishTime); will(returnValue(journeyHistory));
			}
		});
		assertThat(journeyHistoryBuilder.make(), is(journeyHistory));
	}
}
