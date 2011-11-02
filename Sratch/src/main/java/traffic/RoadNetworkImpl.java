package traffic;

import static java.util.Arrays.*;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.Assisted;

@Singleton
public class RoadNetworkImpl implements RoadNetwork {
	private final List<Segment> segments;

	@Inject public RoadNetworkImpl(@Assisted final List<Segment> segments) {
		this.segments = segments;
	}

	public RoadNetworkImpl(final Segment...segments) {
		this(asList(segments));
	}

	@Override
	public List<Segment> segments() {
		return segments;
	}
}
