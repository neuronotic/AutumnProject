package traffic;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

class VehicleManagerImpl implements VehicleManager {
	@Inject Logger logger = Logger.getAnonymousLogger();

	private final List<Vehicle> vehicles = new ArrayList<Vehicle>();
	private final List<JourneyHistory> endedJourneyHistories = new ArrayList<JourneyHistory>();
	private final TimeKeeper timeKeeper;

	@Inject
	public VehicleManagerImpl(final TimeKeeper timeKeeper) {
		this.timeKeeper = timeKeeper;
	}

	@Override
	public void addVehicle(final Vehicle vehicle) {
		vehicles.add(vehicle);
	}

	@Override
	public void step() {
		timeKeeper.step();
		//logger.info(String.format(" \n\n %s STEP", timeKeeper.currentTime()));
		for (final Vehicle vehicle : new ArrayList<Vehicle>(vehicles)) {
			vehicle.step();
		}
	}

	public void step(final int steps) {
		for (int j = 0; j < steps; j++) {
			step();
		}
	}

	@Override
	public List<Vehicle> vehicles() {
		return vehicles;
	}

	@Override
	public List<JourneyHistory> getEndedJourneyHistories() {
		return endedJourneyHistories;
	}

	@Subscribe
	@Override
	public void journeyEnded(final JourneyEndedMessage journeyEndedMessage) {
		//logger.info(String.format(" JOURNEYENDED at time %s for %s", timeKeeper.currentTime(), journeyEndedMessage.vehicle()));

		endedJourneyHistories.add(journeyEndedMessage.journeyHistory());
		vehicles.remove(journeyEndedMessage.vehicle());
	}
}
