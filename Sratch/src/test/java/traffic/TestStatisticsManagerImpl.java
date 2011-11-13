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
	private final NetworkFlux networkFlux = context.mock(NetworkFlux.class);
	private final StatisticsManager statisticsManager = new StatisticsManagerImpl(network);

	@Test
	public void currentNetworkFluxDelegatesToNetwork() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(network).flux(); will(returnValue(networkFlux));
			}
		});
		assertThat(statisticsManager.currentNetworkFlux(), is(networkFlux));
	}

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
