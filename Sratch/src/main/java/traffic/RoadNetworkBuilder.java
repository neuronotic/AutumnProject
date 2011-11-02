package traffic;

public interface RoadNetworkBuilder {
	RoadNetwork make();
	RoadNetworkBuilder withSegment(SegmentBuilder segmentBuilder);

}
