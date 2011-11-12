package traffic;

import java.util.List;


public interface NetworkFactory {

	Network createNetwork(List<Segment> segments);


}
