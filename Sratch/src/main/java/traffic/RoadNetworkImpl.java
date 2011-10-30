package traffic;


public class RoadNetworkImpl implements RoadNetwork {
	private final Segment segment;

	public RoadNetworkImpl(final Segment...segments) {
		segment = segments[0];
	}

	@Override
	public Segment shortestRoute(final Junction origin, final Junction destination) {
		return segment;
	}
}
