package traffic;

import java.util.logging.Logger;

import com.google.inject.Inject;


public class EquisaturationStrategy implements JunctionControllerStrategy {
	@Inject Logger logger = Logger.getAnonymousLogger();

	@Override
	public void step(final LightsManager lightsManager) {
		Link linkWithHighestCongestion = null;
		double highestCongestion = Double.NEGATIVE_INFINITY;

		for (final Link link : lightsManager.linksInOrderAdded()) {
			if (link.congestion() > highestCongestion) {
				linkWithHighestCongestion = link;
				highestCongestion = link.congestion();
			}
		}
		lightsManager.setAllRed();
		lightsManager.setGreen(linkWithHighestCongestion);

		//logger.info(String.format("Equisaturation, lightsManager state: %s  , link with highest congestion: %s with cong: %s", lightsManager, linkWithHighestCongestion.name(), linkWithHighestCongestion.congestion()));


	}
}
