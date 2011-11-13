package traffic;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class NetworkImpl implements Network {
	private final List<Link> links;
	private final Set<Junction> junctions;
	private final NetworkOccupancyFactory networkOccupancyFactory;

	@Inject public NetworkImpl(
			final NetworkOccupancyFactory networkOccupancyFactory,
			@Assisted final List<Link> links) {
		this.networkOccupancyFactory = networkOccupancyFactory;
		this.links = links;
		junctions = buildSetOfJunctions();
	}

	@Override
	public List<Link> links() {
		return links;
	}

	@Override
	public Collection<Junction> junctions() {
		return junctions;
	}

	private Set<Junction> buildSetOfJunctions() {
		final Set<Junction> junctions = new HashSet<Junction>();
		for (final Link link : links) {
			junctions.add(link.inJunction());
			junctions.add(link.outJunction());
		}
		return junctions;
	}

	@Override
	public void step() {
		for (final Junction junction : junctions) {
			junction.step();
		}
	}

	@Override
	public NetworkOccupancy occupancy() {
		final Set<JunctionOccupancy> junctionOccupancies = new HashSet<JunctionOccupancy>();
		for (final Junction junction : junctions) {
			junctionOccupancies.add(junction.occupancy());
		}
		return networkOccupancyFactory.create(junctionOccupancies);
	}

	@Override
	public NetworkFlux flux() {
		// TODO Auto-generated method stub
		return null;

	}

}
