package traffic;

import java.util.List;


public class PeriodicDutyCycleStrategy implements JunctionControllerStrategy {

	int currentGreenIndex = 0;

	@Override
	public void step(final LightsManager lightsManager) {
		final List<Link> links = lightsManager.linksInOrderAdded();
		currentGreenIndex = currentGreenIndex % links.size();
		lightsManager.setAllRed();
		lightsManager.setGreen(links.get(currentGreenIndex));
		currentGreenIndex++;
	}

	@Override
	public void addIncomingLink(final Link link) {
		// TODO Auto-generated method stub
	}

}
