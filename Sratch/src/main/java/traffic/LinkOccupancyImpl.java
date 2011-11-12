package traffic;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class LinkOccupancyImpl implements LinkOccupancy {
	@Inject LinkOccupancyImpl(@Assisted final Link link, @Assisted final Occupancy occupancy) {

	}
}
