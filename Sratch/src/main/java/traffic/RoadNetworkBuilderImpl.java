package traffic;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

public class RoadNetworkBuilderImpl implements RoadNetworkBuilder {

	private final RoadNetworkFactory roadNetworkFactory;
	private final List<SegmentBuilder> segmentBuilders = new ArrayList<SegmentBuilder>();
	private final List<Segment> segments = new ArrayList<Segment>();

	@Inject public RoadNetworkBuilderImpl(final RoadNetworkFactory roadNetworkFactory) {
		this.roadNetworkFactory = roadNetworkFactory;
	}

	@Override
	public RoadNetwork make() {
		final List<Segment> segmentsToMakeRoadNetworkWith = new ArrayList<Segment>(segments);
		for (final SegmentBuilder builder : segmentBuilders) {
			segmentsToMakeRoadNetworkWith.add(builder.make());
		}
		return roadNetworkFactory.createRoadNetwork(segmentsToMakeRoadNetworkWith);
	}

	@Override
	public RoadNetworkBuilder withSegment(final SegmentBuilder segmentBuilder) {
		segmentBuilders.add(segmentBuilder);
		return this;
	}

	@Override
	public RoadNetworkBuilder withSegment(final Segment segment) {
		segments.add(segment);
		return this;
	}

}
