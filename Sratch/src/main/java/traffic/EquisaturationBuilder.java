package traffic;

public interface EquisaturationBuilder extends JunctionControllerBuilder {

	EquisaturationBuilder withPeriod(SimulationTime period);
	EquisaturationBuilder withSwitchingDelay(SimulationTime switchingDelay);

}
