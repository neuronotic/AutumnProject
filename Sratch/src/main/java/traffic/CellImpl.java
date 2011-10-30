package traffic;

import static org.apache.commons.lang3.builder.ToStringBuilder.*;
import static traffic.RoadNetworkToStringStyle.*;

public class CellImpl implements Cell {
	@Override
	public void enter(final Vehicle vehicle) {

	}

	@Override
	public String toString() {
		return reflectionToString(this, roadNetworkToStringStyle());
	}
}
