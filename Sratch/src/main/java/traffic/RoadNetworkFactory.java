package traffic;

import java.util.ArrayList;
import java.util.List;

public class RoadNetworkFactory {
	public static Junction junction(final String name) {
		return new JunctionImpl(name);
	}

	public static CellChainFactory cellChainOfLength(final int cellCount) {
		return new CellChainFactory() {
			@Override
			public CellChain make(final Segment segment) {
				final List<Cell> cells = new ArrayList<Cell>();
				for (int i = 0; i < cellCount; i++) {
					cells.add(new CellImpl(null, 0));
				}
				return new CellChainImpl(cells);
			}
		};
	}

	public static TripOrigin tripFrom(final Junction junction) {
		return new TripOriginImpl(junction);
	}

	public static RoadNetwork roadNetwork(final Segment...segments) {
		return new RoadNetworkImpl(segments);
	}

	public static Segment segment(final String segmentName, final Junction inJunction, final CellChainFactory cellChainFactory, final Junction outJunction) {
		return new SegmentImpl(segmentName, inJunction, cellChainFactory, outJunction);
	}
}
