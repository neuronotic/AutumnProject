package traffic;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

class VehicleManagerImpl implements VehicleManager {
	@Inject Logger logger = Logger.getAnonymousLogger();

	private final List<Vehicle> vehicles = new ArrayList<Vehicle>();

	private final VehicleUpdateOrdering vehicleUpdateOrder;

	@Inject
	public VehicleManagerImpl(final VehicleUpdateOrdering vehicleUpdateOrder) {
		this.vehicleUpdateOrder = vehicleUpdateOrder;
	}

	@Override
	public void step() {
		for (final Vehicle vehicle : vehicleUpdateOrder.vehicleSequence(vehicles)) {
			vehicle.step();
		}
		//logger.info(String.format(" VEHICLEMANAGER: %s", vehicles));
		//for (final Vehicle vehicle : new ArrayList<Vehicle>(vehicles)) {
		//	vehicle.step();
		//}
	}

	@Override
	public List<Vehicle> vehicles() {
		return vehicles;
	}

	@Subscribe
	@Override
	public void journeyEnded(final JourneyEndedMessage journeyEndedMessage) {
		vehicles.remove(journeyEndedMessage.vehicle());
	}

	@Subscribe
	@Override
	public void journeyStarted(final JourneyStartedMessage journeyStartedMessage) {
		vehicles.add(journeyStartedMessage.vehicle());
	}
}
