package traffic;

public interface JunctionOccupancyBuilder {

	JunctionOccupancyBuilder withJunction(Junction junction);
	JunctionOccupancyBuilder withOccupancy(Occupancy occupancy);
	JunctionOccupancyBuilder withIncomingLinkOccupancy(LinkOccupancy linkOccupancy);
	JunctionOccupancy make();

}
