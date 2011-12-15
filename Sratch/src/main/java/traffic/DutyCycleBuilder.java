package traffic;

public interface DutyCycleBuilder extends JunctionControllerBuilder {

	DutyCycleBuilder withPeriod(SimulationTime period);
	DutyCycleBuilder withSwitchingDelay(SimulationTime switchingDelay);

}
