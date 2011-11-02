package traffic;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

public class RoadNetworkBuilderImpl implements RoadNetworkBuilder {

	private final RoadNetworkFactory roadNetworkFactory;
	private final List<SegmentBuilder> segmentBuilders = new ArrayList<SegmentBuilder>();

	@Inject public RoadNetworkBuilderImpl(final RoadNetworkFactory roadNetworkFactory) {
		this.roadNetworkFactory = roadNetworkFactory;
	}

	@Override
	public RoadNetwork make() {
		final List<Segment> segments = new ArrayList<Segment>();
		for (final SegmentBuilder builder : segmentBuilders) {
			segments.add(builder.make());
		}
		return roadNetworkFactory.createRoadNetwork(segments);
	}

	@Override
	public RoadNetworkBuilder withSegment(final SegmentBuilder segmentBuilder) {
		segmentBuilders.add(segmentBuilder);
		return this;
	}

}
