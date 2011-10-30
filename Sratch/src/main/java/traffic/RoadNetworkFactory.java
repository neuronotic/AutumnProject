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

	public static RoadNetwork roadNetwork(final Segment...segments) {
		return new RoadNetworkImpl(segments);
	}

	public static Segment segment(final String segmentName, final Junction inJunction, final CellChain cellChain, final Junction outJunction) {
		return new SegmentImpl(segmentName, inJunction, cellChain, outJunction);
	}
}
