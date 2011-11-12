package traffic;

import java.util.logging.Logger;

import com.google.inject.Inject;

public class StatisticsImpl implements Statistics {
	@Inject Logger logger = Logger.getAnonymousLogger();

	@Override
	public void step(final RoadNetwork roadNetwork) {
		logger.info(String.format("LOG JUNCTION MEASURES, capacity:%s, occup:%s", networkCapacity(roadNetwork), networkOccupancy(roadNetwork) ));
		for (final Junction junction : roadNetwork.junctions()) {
			logger.info(String.format("--%s cap: %s, occu: %s, queue: %s", junction.name(), junctionCapacity(junction), junctionOccupancy(junction), junction.vehiclesWaitingToJoin()));
		}
		for (final Segment segment : roadNetwork.segments()) {

			logger.info(String.format("--%s cap:%s, occ: %s, [%s]", segment.name(), segment.cellCount(), segment.occupiedCount(), array2String(segmentOccupancyBinaryArray(segment))));
		}
	}

	private String array2String(final int[] array) {
		final StringBuffer str = new StringBuffer(array.length);
		for (final int i : array) {
			str.append(i);
		}
		return str.toString();
	}

	private int[] segmentOccupancyBinaryArray(final Segment segment) {
		final int[] occupancyArray = new int[segment.cellCount()];
		for (int i=0; i<segment.cellCount(); i++) {
			occupancyArray[i] = segment.cellChain().getCellAtIndex(i).isOccupied() ? 1 : 0;
		}
		return occupancyArray;
	}

	private int networkOccupancy(final RoadNetwork roadNetwork) {
		int occupancy = 0;
		for (final Junction junction : roadNetwork.junctions()) {
			occupancy += junctionOccupancy(junction);
		}
		return occupancy;
	}

	private int junctionOccupancy(final Junction junction) {
		return junction.inBoundSegmentsOccupancy() + junction.occupancy();
	}

	private int networkCapacity(final RoadNetwork roadNetwork) {
		int capacity = 0;
		for (final Junction junction : roadNetwork.junctions()) {
			capacity += junctionCapacity(junction);
		}
		return capacity;
	}

	private int junctionCapacity(final Junction junction) {
		return junction.inBoundSegmentsCapacity() + junction.capacity();
	}

	@Override
	public NetworkOccupancy currentNetworkOccupancy() {
		// TODO Auto-generated method stub
		return null;
	}
}


