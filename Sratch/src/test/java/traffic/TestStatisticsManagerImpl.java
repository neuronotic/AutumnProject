package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestStatisticsManagerImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Network network = context.mock(Network.class);
	private final NetworkOccupancy networkOccupancy = context.mock(NetworkOccupancy.class);

	private final StatisticsManager statisticsManager = new StatisticsManagerImpl(network);

	@Test
	public void currentNetworkOccupancyDelegatesToNetwork() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(network).occupancy(); will(returnValue(networkOccupancy));
			}
		});
		assertThat(statisticsManager.currentNetworkOccupancy(), is(networkOccupancy));
	}
}
