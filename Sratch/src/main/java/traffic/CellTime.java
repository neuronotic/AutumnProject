package traffic;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class CellTime {
	private final Cell cell;
	private final int time;

	private CellTime(final Cell cell, final int time) {
		this.cell = cell;
		this.time = time;
	}

	static public CellTime cellTime(final Cell cell, final int time) {
		return new CellTime(cell, time);
	}

	@Override
	public boolean equals(final Object that) {
		if (that != null && that.getClass().equals(this.getClass())) {
			final CellTime thatCellTime = (CellTime) that;
			return new EqualsBuilder()
				.append(time, thatCellTime.time)
				.append(cell, thatCellTime.cell)
				.isEquals();
		} return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(time)
			.append(cell)
			.toHashCode();
	}

	public int getTime() {
		return time;
	}

	public Cell getCell() {
		return cell;
	}
}
