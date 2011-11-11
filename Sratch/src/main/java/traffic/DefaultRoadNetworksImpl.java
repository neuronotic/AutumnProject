package traffic;

import com.google.inject.Inject;

public class DefaultRoadNetworksImpl implements DefaultRoadNetworks {

	private final JunctionFactory junctionFactory;
	private final RoadNetworkBuilderFactory roadNetworkBuilderFactory;
	private final SegmentBuilderFactory segmentBuilderFactory;

	@Inject DefaultRoadNetworksImpl(final JunctionFactory junctionFactory, final SegmentBuilderFactory segmentBuilderFactory, final RoadNetworkBuilderFactory roadNetworkBuilderFactory) {
		this.junctionFactory = junctionFactory;
		this.segmentBuilderFactory = segmentBuilderFactory;
		this.roadNetworkBuilderFactory = roadNetworkBuilderFactory;
	}

	@Override
	public RoadNetwork xNetwork4Segment() {
		Junction junction0, junction1, junction2, junction3, junction4;
		junction0 = junctionFactory.createJunction("junction0");
		junction1 = junctionFactory.createJunction("junction1");
		junction2 = junctionFactory.createJunction("junction2");
		junction3 = junctionFactory.createJunction("junction3");
		junction4 = junctionFactory.createJunction("junction4");

		final int segmentLength = 5;

		return roadNetworkBuilder()
			.withSegment( segment()
				.withName("segment0")
				.withInJunction(junction0)
				.withOutJunction(junction1)
				.withLength(segmentLength)
				.make())
			.withSegment( segment()
				.withName("segment1")
				.withInJunction(junction1)
				.withOutJunction(junction2)
				.withLength(segmentLength)
				.make())
			.withSegment( segment()
				.withName("segment2")
				.withInJunction(junction3)
				.withOutJunction(junction1)
				.withLength(segmentLength)
				.make())
			.withSegment( segment()
				.withName("segment3")
				.withInJunction(junction1)
				.withOutJunction(junction4)
				.withLength(segmentLength)
				.make())
			.make();
	}

	private RoadNetworkBuilder roadNetworkBuilder() {
		return roadNetworkBuilderFactory.create();
	}

	private SegmentBuilder segment() {
		return segmentBuilderFactory.create();
	}
}
