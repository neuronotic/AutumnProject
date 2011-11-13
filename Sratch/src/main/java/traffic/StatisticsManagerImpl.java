package traffic;

import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class StatisticsManagerImpl implements StatisticsManager {
	@Inject Logger logger = Logger.getAnonymousLogger();


	private final Network network;

	@Inject StatisticsManagerImpl(@Assisted final Network network) {
		this.network = network;
	}

	@Override
	public NetworkOccupancy currentNetworkOccupancy() {
		return network.occupancy();
	}

	@Override
	public NetworkFlux currentNetworkFlux() {
		return network.flux();
	}

	@Override
	public void step(final Network network) {


/*		logger.info(String.format("LOG JUNCTION MEASURES, capacity:%s, occup:%s", networkCapacity(network), networkOccupancy(network) ));
		for (final Junction junction : network.junctions()) {
			logger.info(String.format("--%s cap: %s, occu: %s, queue: %s", junction.name(), junctionCapacity(junction), junctionOccupancy(junction), junction.vehiclesWaitingToJoin()));
		}
		for (final Link link : network.links()) {

			logger.info(String.format("--%s cap:%s, occ: %s, [%s]", link.name(), link.cellCount(), link.occupiedCount(), array2String(linktOccupancyBinaryArray(link))));
		}
*/
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
}


