package traffic;

import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class PeriodicDutyCycle implements JunctionControllerStrategy {
	@Inject Logger logger = Logger.getAnonymousLogger();

	@Inject PeriodicDutyCycle(
			@Assisted final Junction junction,
			@Assisted final SimulationTime period,
			final TimeKeeper timeKeeper) {

	}

	@Override
	public void step() {
		logger.info(String.format("periodDutyCycle"));
	}

}
