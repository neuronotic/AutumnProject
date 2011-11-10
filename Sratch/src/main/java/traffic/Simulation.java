package traffic;

import java.util.List;

public interface Simulation {

	void step(int timesteps);
	void step();
	List<JourneyHistory> getJourneyEndedHistories();
}
