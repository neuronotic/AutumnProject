package traffic;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class OccupancyImpl implements Occupancy {
	@Inject OccupancyImpl(@Assisted("occupancy") final int occupancy, @Assisted("capacity") final int capacity) {

	}
}
