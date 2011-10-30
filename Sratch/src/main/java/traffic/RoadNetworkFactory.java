package traffic;


public class RoadNetworkFactory {
	public static TripOrigin tripFrom(final Junction junction) {
		return new TripOriginImpl(junction);
	}

	public static RoadNetwork roadNetwork(final Segment...segments) {
		return new RoadNetworkImpl(segments);
	}
}
