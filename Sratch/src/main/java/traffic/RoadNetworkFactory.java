package traffic;

import java.util.ArrayList;
import java.util.List;

public class RoadNetworkFactory {
	public static Junction junction(final String name) {
		return new JunctionImpl(name);
	}

	public static CellChain cellChainOfLength(final int cellCount) {
		final List<Cell> cells = new ArrayList<Cell>();
		for (int i = 0; i < cellCount; i++) {
			cells.add(new CellImpl());
		}
		return new CellChainImpl(cells);
	}

	public static TripOrigin tripFrom(final Junction junction) {
		return new TripOriginImpl(junction);
	}

	public static RoadNetwork roadNetwork(final Segment segment) {
		return new RoadNetworkImpl(segment);
	}

	public static AdjacentJunctionPair adjacent(final Junction inJunction, final Junction outJunction) {
		return new AdjacentJunctionPairImpl(inJunction, outJunction);
	}
}
