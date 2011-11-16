package traffic;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class PeriodicDutyCycle implements JunctionControllerStrategy {
	@Inject Logger logger = Logger.getAnonymousLogger();
	private final TimeKeeper timeKeeper;
	private final SimulationTime period;
	private final Junction junction;
	private final List<Link> links = new ArrayList<Link>();
	private int currentGreenIndex;

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
		if (timeKeeper.currentTime().value() % period.value() == 0) {
			lightsManager.setAllRed();
			currentGreenIndex = currentGreenIndex % links.size();
			lightsManager.setGreen(links.get(currentGreenIndex));
			currentGreenIndex++;
		}

		final StringBuffer lights = new StringBuffer("(");
		for (final Link link : links) {
			lights.append(String.format("%s:%s, ", link.name(), lightsManager.isGreen(link)));
		}
		lights.append(")");

		logger.info(String.format("periodDutyCycle %s / %s - lights are green: %s ", timeKeeper.currentTime().value(), timeKeeper.currentTime().value() % period.value(), lights.toString()));
	}

	@Override
	public void addIncomingLink(final Link link) {
		links.add(link);
	}
}
