package traffic;

import static traffic.SimulationTime.*;

import com.google.inject.Singleton;

@Singleton
public class TimeKeeperImpl implements TimeKeeper {
	int currentTime = 0;

	@Override
	public SimulationTime currentTime() {
		return time(currentTime);
	}

	@Override
	public void step() {
		currentTime += 1;
	}

}
