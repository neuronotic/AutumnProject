package traffic;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class EquisaturationController implements JunctionController {

	private final TimeKeeper timeKeeper;
	private final SimulationTime period;

	@Inject public EquisaturationController(
			final TimeKeeper timeKeeper,
			@Assisted final SimulationTime period) {
				this.timeKeeper = timeKeeper;
				this.period = period;
	}

	@Override
	public void step(final LightsManager lightsManager) {
		if (timeKeeper.currentTime().isHarmonicOf(period)) {
			lightsManager.setAllRed();
			lightsManager.setGreen(linkWithHighestCongestion(lightsManager));
		}
	}

	private Link linkWithHighestCongestion(final LightsManager lightsManager) {
		Link linkWithHighestCongestion = null;
		double highestCongestion = Double.NEGATIVE_INFINITY;
		for (final Link link : lightsManager.linksInOrderAdded()) {
			if (link.congestion() > highestCongestion) {
				linkWithHighestCongestion = link;
				highestCongestion = link.congestion();
			}
		}
		return linkWithHighestCongestion;
	}

}
