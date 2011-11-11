package traffic;

import java.util.List;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class SimulationImpl implements Simulation {
	@Inject Logger logger = Logger.getAnonymousLogger();

	private final RoadNetwork roadNetwork;
	private final VehicleManager vehicleManager;
	private final VehicleCreator vehicleCreator;

	private final TimeKeeper timeKeeper;

	private final Statistics statistics;

	@Inject
	SimulationImpl(
			@Assisted final RoadNetwork roadNetwork,
			@Assisted final List<FlowGroup> flowGroups,
			final TimeKeeper timeKeeper,
			final Statistics statistics,
			final VehicleManager vehicleManager,
			final VehicleCreatorFactory vehicleCreatorFactory) {
		this.roadNetwork = roadNetwork;
		this.timeKeeper = timeKeeper;
		this.statistics = statistics;
		this.vehicleManager = vehicleManager;
		vehicleCreator = vehicleCreatorFactory.create(flowGroups);
	}

	@Override
	public void step() {
		roadNetwork.step();
		vehicleCreator.step();
		vehicleManager.step();
		timeKeeper.step();
	}

	@Override
	public void step(final int timesteps) {
		for (int i=0; i<timesteps; i++) {
			step();
		}
	}

	@Override
	public List<JourneyHistory> getEndedJourneyHistories() {
		return vehicleManager.getEndedJourneyHistories();
	}

	@Override
	public Statistics statistics() {
		return statistics;
	}
}
