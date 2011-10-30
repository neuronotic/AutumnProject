package traffic;


public class VehicleManagerFactory {
	public static VehicleManager vehicleManager() {
		return new VehicleManagerImpl();
	}
}
