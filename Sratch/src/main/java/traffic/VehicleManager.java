package traffic;

import java.util.List;


public interface VehicleManager {
	void addVehicle(Vehicle vehicle);
	List<Vehicle> vehicles();


	void step();

	void step(int i);
	List<JourneyHistory> getCompletedJourneyHistories();
}
