package traffic;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class JunctionOccupancyImpl implements JunctionOccupancy {
	private final Set<LinkOccupancy> incomingLinkOccupancies;
	private final Junction junction;
	private final Occupancy occupancy;

	@Inject JunctionOccupancyImpl(
			@Assisted final Junction junction,
			@Assisted final Occupancy occupancy,
			@Assisted final Set<LinkOccupancy> incomingLinkOccupancies) {
		this.junction = junction;
		this.occupancy = occupancy;
		this.incomingLinkOccupancies = incomingLinkOccupancies;
	}

	@Override
	public boolean equals(final Object that) {
		if (that != null && that.getClass().equals(this.getClass())) {
			final JunctionOccupancyImpl thatJunctionOccupancy = (JunctionOccupancyImpl) that;
			return new EqualsBuilder()
				.append(junction, thatJunctionOccupancy.junction)
				.append(occupancy, thatJunctionOccupancy.occupancy)
				.append(incomingLinkOccupancies, thatJunctionOccupancy.incomingLinkOccupancies)
				.isEquals();
		} return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(junction)
			.append(occupancy)
			.append(incomingLinkOccupancies)
			.toHashCode();
	}

	@Override
	public String toString() {
		return String.format("<%s, %s (%s)>", junction.name(), occupancy, incomingLinkOccupancies);
	}

}
