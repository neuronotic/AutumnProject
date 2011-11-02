package traffic;

import java.util.List;


public interface RoadNetworkFactory {

	RoadNetwork createRoadNetwork(List<Segment> segments);


}
