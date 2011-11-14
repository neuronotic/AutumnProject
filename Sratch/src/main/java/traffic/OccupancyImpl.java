package traffic;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class OccupancyImpl implements Occupancy {
	private final int occupancy;
	private final int capacity;

	@Inject OccupancyImpl(@Assisted("occupancy") final int occupancy, @Assisted("capacity") final int capacity) {
		this.occupancy = occupancy;
		this.capacity = capacity;
	}

	@Override
	public boolean equals(final Object that) {
		if (that != null && that.getClass().equals(this.getClass())) {
			final OccupancyImpl thatOccupancy = (OccupancyImpl) that;
			return new EqualsBuilder()
				.append(occupancy, thatOccupancy.occupancy)
				.append(capacity, thatOccupancy.capacity)
				.isEquals();
		} return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(occupancy)
			.append(capacity)
			.toHashCode();
	}

	@Override
	public String toString() {
		return String.format("%s/%s", occupancy, capacity);
	}

	@Override
	public int occupancy() {
		return occupancy;
	}

	@Override
	public int capacity() {
		return capacity;
	}
}
