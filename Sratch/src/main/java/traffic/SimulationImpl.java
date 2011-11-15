package traffic;

import java.util.List;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class SimulationImpl implements Simulation {
	@Inject Logger logger = Logger.getAnonymousLogger();

	private final Network network;
	private final VehicleManager vehicleManager;
	private final VehicleCreator vehicleCreator;

	private final TimeKeeper timeKeeper;

	private final StatisticsManager statisticsManager;

	@Inject
	SimulationImpl(
			@Assisted final Network network,
			@Assisted final List<FlowGroup> flowGroups,
			final TimeKeeper timeKeeper,
			final StatisticsManagerFactory statisticsFactory,
			final VehicleManager vehicleManager,
			final VehicleCreatorFactory vehicleCreatorFactory) {
		this.network = network;
		this.timeKeeper = timeKeeper;
		statisticsManager = statisticsFactory.create(network);
		this.vehicleManager = vehicleManager;
		vehicleCreator = vehicleCreatorFactory.create(flowGroups);
	}

	@Override
	public void step() {
		network.step();
		vehicleCreator.step();
		vehicleManager.step();
		statisticsManager.step();
		timeKeeper.step();
	}

	@Override
	public void step(final int timesteps) {
		for (int i=0; i<timesteps; i++) {
			step();
		}
	}

	@Override
	public StatisticsManager statistics() {
		return statisticsManager;
	}

	@Override
	public SimulationTime time() {
		return timeKeeper.currentTime();
	}
}
