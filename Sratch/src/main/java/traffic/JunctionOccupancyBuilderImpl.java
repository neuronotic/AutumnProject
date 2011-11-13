package traffic;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

public class JunctionOccupancyBuilderImpl implements JunctionOccupancyBuilder {

	private final JunctionOccupancyFactory junctionOccupancyFactory;
	private Junction junction;
	private Occupancy occupancy;
	private final List<LinkOccupancy> incomingLinkOccupancies = new ArrayList<LinkOccupancy>();

	@Inject public JunctionOccupancyBuilderImpl(
			final JunctionOccupancyFactory junctionOccupancyFactory) {
				this.junctionOccupancyFactory = junctionOccupancyFactory;
	}

	@Override
	public JunctionOccupancy make() {
		return junctionOccupancyFactory.create(junction, occupancy, incomingLinkOccupancies);
	}

	@Override
	public JunctionOccupancyBuilder withJunction(final Junction junction) {
		this.junction = junction;
		return this;
	}

	@Override
	public JunctionOccupancyBuilder withOccupancy(final Occupancy occupancy) {
		this.occupancy = occupancy;
		return this;
	}

	@Override
	public JunctionOccupancyBuilder withIncomingLinkOccupancy(
			final LinkOccupancy incomingLinkOccupancy) {
		incomingLinkOccupancies.add(incomingLinkOccupancy);
		return this;
	}

}
