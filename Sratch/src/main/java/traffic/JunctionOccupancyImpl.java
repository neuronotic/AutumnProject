package traffic;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class JunctionOccupancyImpl implements JunctionOccupancy {
	private final List<LinkOccupancy> incomingLinkOccupancies;
	private final Junction junction;
	private final Occupancy occupancy;

	@Inject JunctionOccupancyImpl(
			@Assisted final Junction junction,
			@Assisted final Occupancy occupancy,
			@Assisted final List<LinkOccupancy> incomingLinkOccupancies) {
		this.junction = junction;
		this.occupancy = occupancy;
		this.incomingLinkOccupancies = incomingLinkOccupancies;
	}
}
