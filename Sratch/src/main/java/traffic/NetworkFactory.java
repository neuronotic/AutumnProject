package traffic;

import java.util.List;


public interface NetworkFactory {

	Network createNetwork(List<Link> links);


}
