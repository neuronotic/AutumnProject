package traffic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VehicleUpdateOrderingShuffle implements VehicleUpdateOrdering {
	@Override
	public List<Vehicle> vehicleSequence(final List<Vehicle> vehicles) {
		final List<Vehicle> copyOfList = new ArrayList<Vehicle>(vehicles);
		Collections.shuffle(copyOfList);
		return copyOfList;
	}

}
