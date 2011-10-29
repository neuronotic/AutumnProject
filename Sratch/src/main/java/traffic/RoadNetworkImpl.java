package traffic;


public class RoadNetworkImpl implements RoadNetwork {
	private final Segment segment;

	public RoadNetworkImpl(final Segment segment) {
		this.segment = segment;
	}

	@Override
	public void step() {

	}

	@Override
	public Segment shortestRoute(final Junction origin, final Junction destination) {
		return segment;
	}
}
