package traffic;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class LinkOccupancyImpl implements LinkOccupancy {
	private final Occupancy occupancy;
	private final Link link;

	@Inject LinkOccupancyImpl(@Assisted final Link link, @Assisted final Occupancy occupancy) {
		this.link = link;
		this.occupancy = occupancy;
	}

	@Override
	public boolean equals(final Object that) {
		if (that != null && that.getClass().equals(this.getClass())) {
			final LinkOccupancyImpl thatLinkOccupancy = (LinkOccupancyImpl) that;
			return new EqualsBuilder()
				.append(link, thatLinkOccupancy.link)
				.append(occupancy, thatLinkOccupancy.occupancy)
				.isEquals();
		} return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(link)
			.append(occupancy)
			.toHashCode();
	}

	@Override
	public String toString() {
		return String.format("%s with %s", link.name(), occupancy);
	}

	@Override
	public int capacity() {
		return occupancy.capacity();
	}

	@Override
	public int occupancy() {
		return occupancy.occupancy();
	}

	@Override
	public Link link() {
		return link;
	}
}
