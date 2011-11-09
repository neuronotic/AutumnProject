package traffic;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

class VehicleManagerImpl implements VehicleManager {
	private final List<Vehicle> vehicles = new ArrayList<Vehicle>();
	private final List<JourneyHistory> completedJourneyHistories = new ArrayList<JourneyHistory>();
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
		for (final Vehicle vehicle : vehicles) {
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
	public List<JourneyHistory> getCompletedJourneyHistories() {
		return completedJourneyHistories;
	}

	@Override
	public void journeyCompleted(final JourneyCompletedMessage journeyCompletedMessage) {
		completedJourneyHistories.add(journeyCompletedMessage.journeyHistory());
		vehicles.remove(journeyCompletedMessage.vehicle());
	}
}
