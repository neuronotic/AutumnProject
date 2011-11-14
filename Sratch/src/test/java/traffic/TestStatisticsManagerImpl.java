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
	private final FluxReceiverFactory fluxReceiverFactory = context.mock(FluxReceiverFactory.class);
	private final FluxReceiver fluxReceiver = context.mock(FluxReceiver.class);
	private final NetworkOccupancyTimeSeries networkOccupancyTimeSeries = context.mock(NetworkOccupancyTimeSeries.class);

	private final StatisticsManager statisticsManager = statisticsManager();
	@Test
	public void currentNetworkFluxDelegatesToFluxReceiver() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(fluxReceiver).currentNetworkFlux(); will(returnValue(networkFlux));
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

	private StatisticsManager statisticsManager() {
		context.checking(new Expectations() {
			{
				oneOf(fluxReceiverFactory).create(network); will(returnValue(fluxReceiver));
			}
		});
		return new StatisticsManagerImpl(network, networkOccupancyTimeSeries, fluxReceiverFactory);
	}
}
