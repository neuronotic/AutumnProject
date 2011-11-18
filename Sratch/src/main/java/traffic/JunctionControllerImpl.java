package traffic;


public class JunctionControllerImpl implements JunctionController {

	private final TimeKeeper timeKeeper;
	private final JunctionControllerStrategy junctionControllerStrategy;
	private final SimulationTime period;

	public JunctionControllerImpl(final TimeKeeper timeKeeper,
			final JunctionControllerStrategy junctionControllerStrategy,
			final SimulationTime period) {
				this.timeKeeper = timeKeeper;
				this.junctionControllerStrategy = junctionControllerStrategy;
				this.period = period;
	}

	@Override
	public void step(final LightsManager lightsManager) {
		if (timeKeeper.currentTime().isHarmonicOf(period)) {
			junctionControllerStrategy.step(lightsManager);
		}
	}


}
