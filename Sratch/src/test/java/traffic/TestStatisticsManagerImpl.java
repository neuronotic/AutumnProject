package traffic;

import static java.util.Arrays.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.SimulationTime.*;

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

	private final CellOccupantDepartedMessage departureMessage0 = context.mock(CellOccupantDepartedMessage.class, "departedMessage0");
	private final CellOccupantDepartedMessage departureMessage1 = context.mock(CellOccupantDepartedMessage.class, "departedMessage1");

	private final Cell cell = context.mock(Cell.class);
	private final StatisticsManager statisticsManager = statisticsManager();

	@Test
	public void receivingCellDepartureMessageCausesTimeSeriesToBeUpdated() throws Exception {

		context.checking(new Expectations() {
			{
				allowing(departureMessage0).cell(); will(returnValue(cell));
				allowing(departureMessage0).time(); will(returnValue(time(5)));
			}
		});
		statisticsManager.receveCellOccupantDepartedMessage(departureMessage0);
		assertThat(statisticsManager.getCellDepartureTimes(cell), is(asList(time(5))));

		context.checking(new Expectations() {
			{
				allowing(departureMessage1).cell(); will(returnValue(cell));
				allowing(departureMessage1).time(); will(returnValue(time(7)));
			}
		});
		statisticsManager.receveCellOccupantDepartedMessage(departureMessage1);

		assertThat(statisticsManager.getCellDepartureTimes(cell), is(asList(time(5), time(7))));
	}

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
