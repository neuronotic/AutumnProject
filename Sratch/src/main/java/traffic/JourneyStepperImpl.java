package traffic;

import static traffic.RoadNetworkToStringStyle.*;

public class JourneyStepperImpl implements JourneyStepper {
	private int journeyTime;

	@Override
	public void stepped() {
		journeyTime++;
	}

	@Override
	public int journeyTime() {
		return journeyTime;
	}

	@Override
	public String toString() {
		return roadNetworkReflectionToString(this);
	}
}