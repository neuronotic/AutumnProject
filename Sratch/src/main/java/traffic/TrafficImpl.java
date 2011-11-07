package traffic;

import com.google.inject.Inject;

public class TrafficImpl implements Traffic {
	private final VehicleManager vehicleManager = null;

	@Inject public TrafficImpl() {
		//this.vehicleManager = vehicleManager;
	}

	@Override
	public void start() {
		System.out.println("Hello, world!");
		System.out.println("vehical manager is " + vehicleManager);
	}
}
