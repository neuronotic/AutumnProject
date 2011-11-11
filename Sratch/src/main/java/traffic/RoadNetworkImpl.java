package traffic;

import static java.util.Arrays.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class RoadNetworkImpl implements RoadNetwork {
	private final List<Segment> segments;
	private final Set<Junction> junctions;

	@Inject public RoadNetworkImpl(
			@Assisted final List<Segment> segments) {
		this.segments = segments;
		junctions = buildSetOfJunctions();
	}

	public RoadNetworkImpl(final Segment...segments) {
		this(asList(segments));
	}

	@Override
	public List<Segment> segments() {
		return segments;
	}

	@Override
	public Collection<Junction> junctions() {
		return junctions();
	}

	private Set<Junction> buildSetOfJunctions() {
		final Set<Junction> junctions = new HashSet<Junction>();
		for (final Segment segment : segments) {
			junctions.add(segment.inJunction());
			junctions.add(segment.outJunction());
		}
		return junctions;
	}

}
