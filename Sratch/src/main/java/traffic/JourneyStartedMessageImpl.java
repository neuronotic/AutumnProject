package traffic;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class JourneyStartedMessageImpl implements JourneyStartedMessage {

	private final Vehicle vehicle;

	@Inject JourneyStartedMessageImpl(@Assisted final Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	@Override
	public Vehicle vehicle() {
		return vehicle;
	}

}
