package traffic;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class FlowImpl implements Flow {

	private final double probability;
	private final Itinerary itinerary;

	@Inject FlowImpl(@Assisted final Itinerary itinerary, @Assisted final double probability) {
		this.itinerary = itinerary;
		this.probability = probability;

	}

	@Override
	public double probability() {
		return probability;
	}

	@Override
	public Itinerary itinerary() {
		return itinerary;
	}

	@Override
	public String name() {
		final StringBuffer nameBuffer = new StringBuffer();
		for (final Link link : itinerary.route()) {
			nameBuffer.append(link.name());
		}
		return nameBuffer.toString();
	}

}
