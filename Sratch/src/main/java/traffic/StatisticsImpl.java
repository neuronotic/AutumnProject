package traffic;

import java.util.logging.Logger;

import com.google.inject.Inject;

public class StatisticsImpl implements Statistics {
	@Inject Logger logger = Logger.getAnonymousLogger();

	@Override
	public void step(final Network network) {
		logger.info(String.format("LOG JUNCTION MEASURES, capacity:%s, occup:%s", networkCapacity(network), networkOccupancy(network) ));
		for (final Junction junction : network.junctions()) {
			logger.info(String.format("--%s cap: %s, occu: %s, queue: %s", junction.name(), junctionCapacity(junction), junctionOccupancy(junction), junction.vehiclesWaitingToJoin()));
		}
		for (final Link link : network.links()) {

			logger.info(String.format("--%s cap:%s, occ: %s, [%s]", link.name(), link.cellCount(), link.occupiedCount(), array2String(linktOccupancyBinaryArray(link))));
		}
	}

	private String array2String(final int[] array) {
		final StringBuffer str = new StringBuffer(array.length);
		for (final int i : array) {
			str.append(i);
		}
		return str.toString();
	}

	private int[] linktOccupancyBinaryArray(final Link link) {
		final int[] occupancyArray = new int[link.cellCount()];
		for (int i=0; i<link.cellCount(); i++) {
			occupancyArray[i] = link.cellChain().getCellAtIndex(i).isOccupied() ? 1 : 0;
		}
		return occupancyArray;
	}

	private int networkOccupancy(final Network network) {
		int occupancy = 0;
		for (final Junction junction : network.junctions()) {
			occupancy += junctionOccupancy(junction);
		}
		return occupancy;
	}

	private int junctionOccupancy(final Junction junction) {
		return junction.inBoundLinksOccupancy() + junction.occupancy();
	}

	private int networkCapacity(final Network network) {
		int capacity = 0;
		for (final Junction junction : network.junctions()) {
			capacity += junctionCapacity(junction);
		}
		return capacity;
	}

	private int junctionCapacity(final Junction junction) {
		return junction.inBoundLinksCapacity() + junction.capacity();
	}

	@Override
	public NetworkOccupancy currentNetworkOccupancy() {
		// TODO Auto-generated method stub
		return null;
	}
}


