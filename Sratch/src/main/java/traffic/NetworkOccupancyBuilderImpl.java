package traffic;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

public class NetworkOccupancyBuilderImpl implements NetworkOccupancyBuilder {

	private final NetworkOccupancyFactory networkOccupancyFactory;
	private final List<JunctionOccupancyBuilder> junctionOccupancyBuilders =
			new ArrayList<JunctionOccupancyBuilder>();

	@Inject public NetworkOccupancyBuilderImpl(
			final NetworkOccupancyFactory networkOccupancyFactory) {
		this.networkOccupancyFactory = networkOccupancyFactory;
	}

	@Override
	public NetworkOccupancyBuilder withJunctionOccupancy(
			final JunctionOccupancyBuilder junctionOccupancyBuilder) {
		junctionOccupancyBuilders.add(junctionOccupancyBuilder);
		return this;
	}

	@Override
	public NetworkOccupancy make() {
		final List<JunctionOccupancy> junctionOccupancies = new ArrayList<JunctionOccupancy>();
		for (final JunctionOccupancyBuilder builder : junctionOccupancyBuilders) {
			junctionOccupancies.add(builder.make());
		}
		return networkOccupancyFactory.create(junctionOccupancies);
	}

}
