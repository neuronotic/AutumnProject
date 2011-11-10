package traffic;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class VehicleCreatorImpl implements VehicleCreator {

	private final List<FlowGroup> flowGroups;

	@Inject public VehicleCreatorImpl(@Assisted final List<FlowGroup> flowGroups) {
		this.flowGroups = flowGroups;
	}

	@Override
	public void step() {

	}

}
