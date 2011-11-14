package traffic;

import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class StatisticsManagerImpl implements StatisticsManager {
	@Inject Logger logger = Logger.getAnonymousLogger();


	private final Network network;
	private final NetworkOccupancyTimeSeries networkOccupancy;

	private final FluxReceiver fluxReceiver;

	@Inject StatisticsManagerImpl(
			@Assisted final Network network,
			final NetworkOccupancyTimeSeries networkOccupancy,
			final FluxReceiverFactory fluxReceiverFactory) {
		this.network = network;
		this.networkOccupancy = networkOccupancy;
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
		networkOccupancy.addStepData(currentNetworkFlux());
	}
}


