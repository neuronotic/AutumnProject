package traffic;

import static traffic.RoadNetworkToStringStyle.*;

public class JourneyHistoryImpl implements JourneyHistory {
	//@Inject Logger logger = Logger.getAnonymousLogger();

	private int journeyTime;

	@Override
	public void stepped() {
		journeyTime++;
		//logger.info(String.format("Step %s", journeyTime));
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
