package traffic;

import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class PeriodicDutyCycle implements JunctionControllerStrategy {
	@Inject Logger logger = Logger.getAnonymousLogger();
	private final TimeKeeper timeKeeper;
	private final SimulationTime period;
	private final Junction junction;

	@Inject PeriodicDutyCycle(
			@Assisted final Junction junction,
			@Assisted final SimulationTime period,
			final TimeKeeper timeKeeper) {
				this.junction = junction;
				this.period = period;
				this.timeKeeper = timeKeeper;
	}

	@Override
	public void step(final LightsManager lightsManager) {
		logger.info(String.format("periodDutyCycle %s", timeKeeper.currentTime().value() % period.value()));
		//lightsManager.advanceGreenCycle();

	}

	@Override
	public void addIncomingLink(final Link link) {
		// TODO Auto-generated method stub
	}
}
