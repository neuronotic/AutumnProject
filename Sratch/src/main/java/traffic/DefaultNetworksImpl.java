package traffic;

import com.google.inject.Inject;

public class DefaultNetworksImpl implements DefaultNetworks {

	private final JunctionFactory junctionFactory;
	private final NetworkBuilderFactory networkBuilderFactory;
	private final SegmentBuilderFactory segmentBuilderFactory;

	@Inject DefaultNetworksImpl(final JunctionFactory junctionFactory, final SegmentBuilderFactory segmentBuilderFactory, final NetworkBuilderFactory networkBuilderFactory) {
		this.junctionFactory = junctionFactory;
		this.segmentBuilderFactory = segmentBuilderFactory;
		this.networkBuilderFactory = networkBuilderFactory;
	}

	@Override
	public Network xNetwork4Segment() {
		Junction junction0, junction1, junction2, junction3, junction4;
		junction0 = junctionFactory.createJunction("junction0");
		junction1 = junctionFactory.createJunction("junction1");
		junction2 = junctionFactory.createJunction("junction2");
		junction3 = junctionFactory.createJunction("junction3");
		junction4 = junctionFactory.createJunction("junction4");

		final int segmentLength = 5;

		return networkBuilder()
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

	private NetworkBuilder networkBuilder() {
		return networkBuilderFactory.create();
	}

	private SegmentBuilder segment() {
		return segmentBuilderFactory.create();
	}
}
