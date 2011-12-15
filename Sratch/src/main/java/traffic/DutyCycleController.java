package traffic;

import java.util.List;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;


public class DutyCycleController implements JunctionController {
	@Inject Logger logger = Logger.getAnonymousLogger();

	private int currentGreenIndex = 0;
	private int switchingDelayTimer = 0;
	private final TimeKeeper timeKeeper;

	private final SimulationTime period;

	private final SimulationTime switchingDelay;

	@Inject public DutyCycleController(
			final TimeKeeper timeKeeper,
			@Assisted("period") final SimulationTime period,
			@Assisted("switchingDelay") final SimulationTime switchingDelay) {
		this.timeKeeper = timeKeeper;
		this.period = period;
		this.switchingDelay = switchingDelay;
	}

	@Override
	public void step(final LightsManager lightsManager) {
		switchingDelayTimer--;
		if (timeKeeper.currentTime().isHarmonicOf(period)) {
			switchingDelayTimer = switchingDelay.value();
			lightsManager.setAllRed();
			//logger.info(String.format("%s PeriodicDutyCycle, lightsManager state: %s", timeKeeper.currentTime(), lightsManager));
		}
		if (switchingDelayTimer == 0) {
			final List<Link> links = lightsManager.linksInOrderAdded();
			currentGreenIndex = currentGreenIndex % links.size();
			//lightsManager.setAllRed();
			lightsManager.setGreen(links.get(currentGreenIndex));
			currentGreenIndex++;
			//logger.info(String.format("%s PeriodicDutyCycle, lightsManager state: %s", timeKeeper.currentTime(), lightsManager));
		}

	}

}
