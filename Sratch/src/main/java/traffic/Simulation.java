package traffic;

public interface Simulation {

	void step(int timesteps);

	void addVehicle(Vehicle vehicle);

}
