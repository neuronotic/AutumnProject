package traffic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class StatisticsManagerImpl implements StatisticsManager {
	@Inject Logger logger = Logger.getAnonymousLogger();

	private final Network network;
	private final NetworkOccupancyTimeSeries networkOccupancyTimeSeries;
	private final List<JourneyHistory> endedJourneyHistories = new ArrayList<JourneyHistory>();


	private final Map<Cell, List<SimulationTime>> fluxTimes = new HashMap<Cell, List<SimulationTime>>();

	@Inject StatisticsManagerImpl(
			@Assisted final Network network,
			final NetworkOccupancyTimeSeries networkOccupancyTimeSeries
			) {
		this.network = network;
		this.networkOccupancyTimeSeries = networkOccupancyTimeSeries;
	}

	@Override
	public NetworkOccupancy currentNetworkOccupancy() {
		return network.occupancy();
	}

	@Override
	public void step() {
		networkOccupancyTimeSeries.addStepData(currentNetworkOccupancy());
	}

	@Override
	public NetworkOccupancyTimeSeries networkOccupancy() {
		return networkOccupancyTimeSeries;
	}

	@Override
	public List<JourneyHistory> getEndedJourneyHistories() {
		return endedJourneyHistories;
	}

	@Subscribe
	@Override
	public void receiveJourneyHistoryForEndedJourney(final JourneyEndedMessage journeyEndedMessage) {
		//logger.info(String.format(" received journey history for %s", journeyEndedMessage.vehicle()));
		endedJourneyHistories.add(journeyEndedMessage.journeyHistory());
	}

	@Subscribe
	@Override
	public void receveCellOccupantDepartedMessage(
			final CellOccupantDepartedMessage departureMessage) {
		initialiseFluxTimeSequenceForCell(departureMessage.cell());
		addValueToFluxTimeSeries(departureMessage);
	}

	@Override
	public List<SimulationTime> fluxTimes(final Cell cell) {
		return fluxTimes.get(cell);
	}

	private void addValueToFluxTimeSeries(
			final CellOccupantDepartedMessage departureMessage) {
		fluxTimes.get(departureMessage.cell()).add(departureMessage.time());
	}

	private void initialiseFluxTimeSequenceForCell(
			final Cell cell) {
		if (!fluxTimes.containsKey(cell)) {
			fluxTimes.put(cell, new ArrayList<SimulationTime>());
		}
	}

	@Override
	public List<SimulationTime> fluxTimesForLinkHeadCell(final String linkName) {
		// TODO Auto-generated method stub
		return null;
	}
}


