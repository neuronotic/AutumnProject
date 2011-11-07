package traffic;

import com.google.inject.Inject;

public class VehicleManagerBuilderImpl implements VehicleManagerBuilder {

	private final VehicleManagerFactory vehicleManagerFactory;

	@Inject public VehicleManagerBuilderImpl(final VehicleManagerFactory vehicleManagerFactory) {
		this.vehicleManagerFactory = vehicleManagerFactory;
	}

	@Override
	public VehicleManager make() {
		return vehicleManagerFactory.createVehicleManager();
	}

}
