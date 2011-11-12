package traffic;

public interface NetworkBuilder {
	Network make();
	NetworkBuilder withSegment(SegmentBuilder segmentBuilder);
	NetworkBuilder withSegment(Segment segment);

}
