package traffic;

import com.google.inject.Inject;

public class SegmentBuilderImpl implements SegmentBuilder {

	private CellChainBuilder cellChainBuilder;
	private final SegmentFactory segmentFactory;
	private String segmentName;
	private Junction outJunction;
	private Junction inJunction;

	@Inject SegmentBuilderImpl(
			final SegmentFactory segmentFactory,
			final CellChainBuilder cellChainBuilder) {
				this.segmentFactory = segmentFactory;
				this.cellChainBuilder = cellChainBuilder;
	}

	@Override
	public Segment make() {
		final Segment segment = segmentFactory.createSegment(segmentName, inJunction, cellChainBuilder, outJunction);
		outJunction.addIncomingSegment(segment);
		inJunction.addOutgoingSegment(segment);
		return segment;
	}

	@Override
	public SegmentBuilder withInJunction(final Junction inJunction) {
		this.inJunction = inJunction;
		return this;
	}

	@Override
	public SegmentBuilder withOutJunction(final Junction outJunction) {
		this.outJunction = outJunction;
		return this;
	}

	@Override
	public SegmentBuilder withLength(final int segmentLength) {
		cellChainBuilder = cellChainBuilder.cellChainOfLength(segmentLength);
		return this;
	}

	@Override
	public SegmentBuilder withName(final String segmentName) {
		this.segmentName = segmentName;
		return this;
	}
}
