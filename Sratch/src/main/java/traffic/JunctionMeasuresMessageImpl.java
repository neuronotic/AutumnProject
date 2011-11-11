package traffic;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class JunctionMeasuresMessageImpl implements JunctionMeasuresMessage {

	private final SimulationTime time;
	private final Junction junction;
	private final double congestion;
	private final int vehiclesWaiting;

	@Inject JunctionMeasuresMessageImpl(
			@Assisted final Junction junction,
			@Assisted final double congestion,
			@Assisted final int vehiclesWaiting,
			final TimeKeeper timeKeeper) {
		this.junction = junction;
		this.congestion = congestion;
		this.vehiclesWaiting = vehiclesWaiting;
		time = timeKeeper.currentTime();

	}


	@Override
	public Junction junction() {
		return junction;
	}

	@Override
	public SimulationTime time() {
		return time;
	}

	@Override
	public double congestion() {
		return congestion;
	}


	@Override
	public int vehiclesWaiting() {
		return vehiclesWaiting;
	}

}
