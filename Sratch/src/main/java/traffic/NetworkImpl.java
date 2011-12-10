package traffic;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class NetworkImpl implements Network {
	private final Map<String, Link> linksMap;
	private final Set<Junction> junctions;
	private final NetworkOccupancyFactory networkOccupancyFactory;

	@Inject public NetworkImpl(
			final NetworkOccupancyFactory networkOccupancyFactory,
			@Assisted final List<Link> links) {
		this.networkOccupancyFactory = networkOccupancyFactory;
		linksMap = buildLinksMap(links);
		junctions = buildSetOfJunctions();
	}

	private Map<String, Link> buildLinksMap(final List<Link> links) {
		final Map<String, Link> linksMap = new HashMap<String, Link>();
		for (final Link link : links) {
			linksMap.put(link.name(), link);
		}
		return linksMap;
	}

	@Override
	public Collection<Link> links() {
		return linksMap.values();
	}

	@Override
	public Collection<Junction> junctions() {
		return junctions;
	}

	@Override
	public Link linkNamed(final String name) {
		return linksMap.get(name);
	}

	private Set<Junction> buildSetOfJunctions() {
		final Set<Junction> junctions = new HashSet<Junction>();
		for (final Link link : linksMap.values()) {
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

}
