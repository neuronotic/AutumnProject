package traffic;

public interface PeriodicDutyCycleFactory {
	JunctionControllerStrategy create(Junction junction, SimulationTime period);
}
