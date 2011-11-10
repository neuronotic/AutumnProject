package traffic;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class SimulationImpl implements Simulation {

	private final RoadNetwork roadNetwork;
	private final VehicleManager vehicleManager;
	private final VehicleCreator vehicleCreator;
	private final List<FlowGroup> flowGroups;

	@Inject
	SimulationImpl(
			@Assisted final RoadNetwork roadNetwork,
			@Assisted final List<FlowGroup> flowGroups,
			final VehicleManager vehicleManager,
			final VehicleCreatorFactory vehicleCreatorFactory) {
		this.roadNetwork = roadNetwork;
		this.flowGroups = flowGroups;
		this.vehicleManager = vehicleManager;
		vehicleCreator = vehicleCreatorFactory.create(flowGroups);
	}

	@Override
	public void step() {
		vehicleCreator.step();
		for (final Junction junction : roadNetwork.junctions()) {
			junction.step();
		}
		vehicleManager.step();
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
}
