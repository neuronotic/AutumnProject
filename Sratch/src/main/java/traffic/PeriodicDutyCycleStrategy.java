package traffic;

import java.util.List;
import java.util.logging.Logger;

import com.google.inject.Inject;


public class PeriodicDutyCycleStrategy implements JunctionControllerStrategy {
	@Inject Logger logger = Logger.getAnonymousLogger();

	int currentGreenIndex = 0;

	@Override
	public void step(final LightsManager lightsManager) {
		final List<Link> links = lightsManager.linksInOrderAdded();
		currentGreenIndex = currentGreenIndex % links.size();
		lightsManager.setAllRed();
		lightsManager.setGreen(links.get(currentGreenIndex));
		currentGreenIndex++;
		logger.info(String.format("PeriodicDutyCycle, lightsManager state: %s", lightsManager));
	}

}
