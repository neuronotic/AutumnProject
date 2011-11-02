package traffic;

import java.util.ArrayList;
import java.util.List;

//@Singleton
class VehicleManagerImpl implements VehicleManager, JourneyEndListener {
	private final List<Vehicle> vehicles = new ArrayList<Vehicle>();

	@Override
	public void addVehicle(final Vehicle vehicle) {
		vehicles.add(vehicle);
		vehicle.addJourneyEndListener(this);
	}

	@Override
	public void step() {
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
	public void notifyOfJourneyEnd(final Vehicle vehicle) {
		vehicles.remove(vehicle);
	}

	@Override
	public List<Vehicle> vehicles() {
		return vehicles;
	}
}
