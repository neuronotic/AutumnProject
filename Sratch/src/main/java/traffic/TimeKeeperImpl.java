package traffic;

import static traffic.SimulationTime.*;

import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class TimeKeeperImpl implements TimeKeeper {
	@Inject Logger logger = Logger.getAnonymousLogger();

	int currentTime = 0;

	@Override
	public SimulationTime currentTime() {
		return time(currentTime);
	}

	@Override
	public void step() {
		currentTime += 1;
		logger.info(String.format("\n\nSIMULATIONStep to time %d", currentTime));
	}

}
