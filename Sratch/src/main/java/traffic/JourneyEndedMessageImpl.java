package traffic;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class JourneyEndedMessageImpl implements JourneyEndedMessage {

	private final JourneyHistory journeyHistory;
	private final Vehicle vehicle;

	@Inject JourneyEndedMessageImpl(
			@Assisted final Vehicle vehicle,
			@Assisted final JourneyHistory journeyHistory) {
				this.vehicle = vehicle;
				this.journeyHistory = journeyHistory;

	}

	@Override
	public JourneyHistory journeyHistory() {
		return journeyHistory;
	}

	@Override
	public Vehicle vehicle() {
		return vehicle;
	}

}
