package traffic;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class StatisticsManagerImpl implements StatisticsManager {
	@Inject Logger logger = Logger.getAnonymousLogger();


	private final Network network;
	private final NetworkOccupancyTimeSeries networkOccupancyTimeSeries;
	private final List<JourneyHistory> endedJourneyHistories = new ArrayList<JourneyHistory>();

	private final FluxReceiver fluxReceiver;

	@Inject StatisticsManagerImpl(
			@Assisted final Network network,
			final NetworkOccupancyTimeSeries networkOccupancyTimeSeries,
			final FluxReceiverFactory fluxReceiverFactory) {
		this.network = network;
		this.networkOccupancyTimeSeries = networkOccupancyTimeSeries;
		fluxReceiver = fluxReceiverFactory.create(network);
	}

	@Override
	public NetworkOccupancy currentNetworkOccupancy() {
		return network.occupancy();
	}

	@Override
	public NetworkFlux currentNetworkFlux() {
		return fluxReceiver.currentNetworkFlux();
	}

	@Override
	public void step() {
		fluxReceiver.step();
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

}


