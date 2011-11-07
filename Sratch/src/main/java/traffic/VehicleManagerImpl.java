package traffic;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

//@Singleton
class VehicleManagerImpl implements VehicleManager {
	private final List<Vehicle> vehicles = new ArrayList<Vehicle>();

	@Inject
	public VehicleManagerImpl() {}

	@Override
	public void addVehicle(final Vehicle vehicle) {
		vehicles.add(vehicle);
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
	public List<Vehicle> vehicles() {
		return vehicles;
	}

	@Override
	public List<JourneyHistory> getJourneyHistories() {
		// TODO Auto-generated method stub
		return null;

	}
}
