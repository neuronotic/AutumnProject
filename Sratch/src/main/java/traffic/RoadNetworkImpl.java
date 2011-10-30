package traffic;

import static java.util.Arrays.*;

import java.util.List;


public class RoadNetworkImpl implements RoadNetwork {
	private final List<Segment> segments;

	public RoadNetworkImpl(final List<Segment> segments) {
		this.segments = segments;
	}

	public RoadNetworkImpl(final Segment...segments) {
		this(asList(segments));
	}

	@Override
	public List<Segment> route(final Junction origin, final Junction destination) {
		return segments;
	}
}
