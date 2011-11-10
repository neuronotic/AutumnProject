package traffic;

import java.util.List;

public interface VehicleCreatorFactory {

	VehicleCreator create(List<FlowGroup> flowGroups);

}
