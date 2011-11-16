package traffic;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class PeriodicDutyCycle implements JunctionControllerStrategy {

	@Inject PeriodicDutyCycle(
			@Assisted final Junction junction,
			@Assisted final SimulationTime period) {

	}

	@Override
	public void step() {
		// TODO Auto-generated method stub

	}

}
