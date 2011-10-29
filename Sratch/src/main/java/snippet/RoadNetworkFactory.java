package snippet;

public class RoadNetworkFactory {
	public static Junction junction() {
		return new JunctionImpl();
	}

	public static CellChain cellChainOfLength(final int i) {
		return null;
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
