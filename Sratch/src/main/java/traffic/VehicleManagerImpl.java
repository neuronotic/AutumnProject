package traffic;


public class VehicleManagerImpl implements VehicleManager {
	private Vehicle vehicle;

	@Override
	public void addVehicle(final Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	@Override
	public void step() {
		vehicle.step();
	}

	public void step(final int steps) {
		for (int j = 0; j < steps; j++) {
			step();
		}
	}
}
