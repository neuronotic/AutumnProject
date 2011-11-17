package traffic;

import java.util.ArrayList;
import java.util.List;

public class VehicleUpdateOrderingUnmodified implements VehicleUpdateOrdering {

	@Override
	public List<Vehicle> vehicleSequence(final List<Vehicle> vehicles) {
		return new ArrayList<Vehicle>(vehicles);
	}

}
