package traffic;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class NetworkOccupancyImpl implements NetworkOccupancy {
	private final List<JunctionOccupancy> junctionOccupancies;

	@Inject NetworkOccupancyImpl(@Assisted final List<JunctionOccupancy> junctionOccupancies) {
		this.junctionOccupancies = junctionOccupancies;
	}
}
