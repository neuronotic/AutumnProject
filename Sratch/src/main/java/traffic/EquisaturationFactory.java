package traffic;

public interface EquisaturationFactory {
	JunctionControllerStrategy create(Junction junction, SimulationTime period);
}
