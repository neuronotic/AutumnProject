package traffic;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

public class NetworkBuilderImpl implements NetworkBuilder {

	private final NetworkFactory networkFactory;
	private final List<SegmentBuilder> segmentBuilders = new ArrayList<SegmentBuilder>();
	private final List<Segment> segments = new ArrayList<Segment>();

	@Inject public NetworkBuilderImpl(final NetworkFactory networkFactory) {
		this.networkFactory = networkFactory;
	}

	@Override
	public Network make() {
		final List<Segment> segmentsToMakeNetworkWith = new ArrayList<Segment>(segments);
		for (final SegmentBuilder builder : segmentBuilders) {
			segmentsToMakeNetworkWith.add(builder.make());
		}
		return networkFactory.createNetwork(segmentsToMakeNetworkWith);
	}

	@Override
	public NetworkBuilder withSegment(final SegmentBuilder segmentBuilder) {
		segmentBuilders.add(segmentBuilder);
		return this;
	}

	@Override
	public NetworkBuilder withSegment(final Segment segment) {
		segments.add(segment);
		return this;
	}

}
