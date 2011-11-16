package traffic;

import java.util.ArrayList;
import java.util.List;


public class LightsManagerImpl implements LightsManager {

	List<Link> links = new ArrayList<Link>();

	@Override
	public void addIncomingLink(final Link link) {
		links.add(link);
	}

}
