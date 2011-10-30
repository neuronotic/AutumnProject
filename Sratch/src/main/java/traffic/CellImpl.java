package traffic;

import static org.apache.commons.lang3.builder.ToStringBuilder.*;
import static traffic.RoadNetworkToStringStyle.*;

public class CellImpl implements Cell {

	private final Segment segment;
	private final int index;

	public CellImpl(final Segment segment, final int index) {
		this.segment = segment;
		this.index = index;
	}

	@Override
	public void enter(final Vehicle vehicle) {

	}

	@Override
	public String toString() {
		return reflectionToString(this, roadNetworkToStringStyle());
	}

	@Override
	public String name() {
		return String.format("%s[%s]", segment.name(), index);
	}
}
