package traffic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class NetworkOccupancyImpl implements NetworkOccupancy {
	private final Collection<JunctionOccupancy> junctionOccupancies;
	private final Map<Junction, JunctionOccupancy> junctionOccupanciesMap = new HashMap<Junction, JunctionOccupancy>();
	private final Map<Link, LinkOccupancy> linkOccupanciesMap = new HashMap<Link, LinkOccupancy>();

	@Inject NetworkOccupancyImpl(@Assisted final Collection<JunctionOccupancy> junctionOccupancies) {
		this.junctionOccupancies = junctionOccupancies;

		for (final JunctionOccupancy junctionOccupancy : junctionOccupancies) {
			junctionOccupanciesMap.put(junctionOccupancy.junction() , junctionOccupancy);
		}

		for(final JunctionOccupancy junctionOccupancy : junctionOccupancies) {
			for (final LinkOccupancy linkOccupancy : junctionOccupancy.incomingLinkOccupancies()) {
				linkOccupanciesMap.put(linkOccupancy.link(), linkOccupancy);
			}
		}
	}

	@Override
	public boolean equals(final Object that) {
		if (that != null && that.getClass().equals(this.getClass())) {
			final NetworkOccupancyImpl thatNetworkOccupancy = (NetworkOccupancyImpl) that;
			return new EqualsBuilder()
				.append(junctionOccupancies, thatNetworkOccupancy.junctionOccupancies)
				.isEquals();
		} return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(junctionOccupancies)
			.toHashCode();
	}

	@Override
	public String toString() {
		return String.format("NetworkOccupancy(%s)", junctionOccupancies);
	}

	@Override
	public Collection<JunctionOccupancy> junctionOccupancies() {
		return junctionOccupancies;
	}

	@Override
	public JunctionOccupancy occupancyFor(final Junction junction) {
		return junctionOccupanciesMap.get(junction);
	}

	@Override
	public LinkOccupancy occupancyFor(final Link link) {
		return linkOccupanciesMap.get(link);
	}
}
