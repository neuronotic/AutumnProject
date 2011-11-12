package traffic;

public interface NetworkOccupancyBuilder {

	NetworkOccupancyBuilder withJunctionOccupancy(
			JunctionOccupancyBuilder junctionOccupancyBuilder);

	NetworkOccupancy make();

}
