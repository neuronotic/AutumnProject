package traffic;

import static org.apache.commons.lang3.builder.ToStringBuilder.*;
import static traffic.RoadNetworkToStringStyle.*;
import traffic.endtoend.RoadUser;

public class CellImpl implements Cell {
	@Override
	public void enter(final RoadUser roadUser) {

	}

	@Override
	public String toString() {
		return reflectionToString(this, roadNetworkToStringStyle());
	}
}