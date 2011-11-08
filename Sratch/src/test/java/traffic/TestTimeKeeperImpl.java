package traffic;

import static org.hamcrest.MatcherAssert.*;
import static traffic.SimulationMatchers.*;
import static traffic.SimulationTime.*;

import org.junit.Test;

public class TestTimeKeeperImpl {

	private final TimeKeeper timeKeeper = new TimeKeeperImpl();

	@Test
	public void currentTimeCorrespondsToNumberOfTimesStepped() throws Exception {
		timeKeeper.step();
		timeKeeper.step();
		assertThat(timeKeeper.currentTime(), isTime(time(2)));
	}

	@Test
	public void currentTimeInitialisedToZero() throws Exception {
		assertThat(timeKeeper.currentTime(), isTime(time(0)));
	}
}
