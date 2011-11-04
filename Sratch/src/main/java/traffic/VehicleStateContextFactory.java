package traffic;

import java.util.Iterator;

public interface VehicleStateContextFactory {

	VehicleStateContext createStateContext(Iterator<Cell> journeyRemaining);

}
