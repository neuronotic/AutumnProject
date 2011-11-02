package traffic;

public interface SegmentBuilder {

	Segment make();

	SegmentBuilder withInJunction(Junction inJunction);

	SegmentBuilder withOutJunction(Junction outJunction);

	SegmentBuilder withLength(int segmentLength);

	SegmentBuilder withName(String segmentName);

}
