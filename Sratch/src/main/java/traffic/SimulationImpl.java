package traffic;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class SimulationImpl implements Simulation {

	private final RoadNetwork roadNetwork;
	private final VehicleManager vehicleManager;

	@Inject
	SimulationImpl(
			@Assisted final RoadNetwork roadNetwork,
			@Assisted final VehicleManager vehicleManager) {
				this.roadNetwork = roadNetwork;
				this.vehicleManager = vehicleManager;
	}

	@Override
	public void step(final int timesteps) {
		vehicleManager.step(timesteps);
	}

}
