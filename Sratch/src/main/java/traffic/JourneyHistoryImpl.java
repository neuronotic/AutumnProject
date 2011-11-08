package traffic;

import static traffic.RoadNetworkToStringStyle.*;

public class JourneyHistoryImpl implements JourneyHistory {
	//@Inject Logger logger = Logger.getAnonymousLogger();



	@Override
	public String toString() {
		return roadNetworkReflectionToString(this);
	}
}
