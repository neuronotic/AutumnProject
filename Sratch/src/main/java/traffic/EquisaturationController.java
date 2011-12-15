package traffic;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class EquisaturationController implements JunctionController {

	private final TimeKeeper timeKeeper;
	private final SimulationTime period;
	private final SimulationTime switchingDelay;
	private int switchingDelayTimer = 0;
	@Inject public EquisaturationController(
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
		}
		if (switchingDelayTimer == 0) {
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
