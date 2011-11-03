package traffic;

import java.util.ArrayList;
import java.util.List;

//@Singleton
class VehicleManagerImpl implements VehicleManager {
	private final List<Vehicle> vehicles = new ArrayList<Vehicle>();

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
}
