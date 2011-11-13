package traffic;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class NetworkOccupancyImpl implements NetworkOccupancy {
	private final Set<JunctionOccupancy> junctionOccupancies;

	@Inject NetworkOccupancyImpl(@Assisted final Set<JunctionOccupancy> junctionOccupancies) {
		this.junctionOccupancies = junctionOccupancies;
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
}
