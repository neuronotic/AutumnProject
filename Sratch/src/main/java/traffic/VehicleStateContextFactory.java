package traffic;

import java.util.List;

public interface VehicleStateContextFactory {

	VehicleStateContext createStateContext(List<Cell> cells);

}
