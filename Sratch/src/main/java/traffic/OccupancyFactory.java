package traffic;

import com.google.inject.assistedinject.Assisted;

public interface OccupancyFactory {

	Occupancy create(@Assisted("occupancy") int occupancy, @Assisted("capacity") int capacity);

}
