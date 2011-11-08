package traffic;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SimulationTime {
	private final int time;

	private SimulationTime(final int time) {
		this.time = time;
	}

	@Override
	public boolean equals(final Object that) {
		if (that != null && that.getClass().equals(this.getClass())) {
			final SimulationTime thatSimulationTime = (SimulationTime) that;
			return new EqualsBuilder()
				.append(time, thatSimulationTime.time)
				.isEquals();
		} return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(time)
			.toHashCode();
	}

	public static SimulationTime time(final int time) {
		return new SimulationTime(time);
	}

	@Override
	public String toString() {
		return String.format("%d", time);
	}

	public SimulationTime differenceBetween(final SimulationTime other) {
		return time(time - other.time);
	}
}
