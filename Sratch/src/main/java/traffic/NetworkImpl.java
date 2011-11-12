package traffic;

import static java.util.Arrays.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class NetworkImpl implements Network {
	private final List<Link> links;
	private final Set<Junction> junctions;

	@Inject public NetworkImpl(
			@Assisted final List<Link> links) {
		this.links = links;
		junctions = buildSetOfJunctions();
	}

	public NetworkImpl(final Link...links) {
		this(asList(links));
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

}
