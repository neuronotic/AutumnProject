package traffic;

public interface JunctionOccupancyBuilder {

	JunctionOccupancyBuilder withJunction(Junction junction);
	JunctionOccupancyBuilder withOccupancy(int i);
	JunctionOccupancyBuilder withIncomingSegmentOccupancy(SegmentOccupancy segmentOccupancy);

}
