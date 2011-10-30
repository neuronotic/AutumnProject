package traffic;

import static org.junit.Assert.*;
import static traffic.JourneyHistoryMatchers.*;

import org.junit.Test;

public class TestJourneyHistoryImpl {
	@Test
	public void journeyTimeIsIncrementedAtEachTimeStep() throws Exception {
		final JourneyHistoryImpl journeyHistoryImpl = new JourneyHistoryImpl();

		assertThat(journeyHistoryImpl, journeyTime(0));
		journeyHistoryImpl.stepped();

		assertThat(journeyHistoryImpl, journeyTime(1));
		journeyHistoryImpl.stepped();

		assertThat(journeyHistoryImpl, journeyTime(2));
		journeyHistoryImpl.stepped();
	}
}
