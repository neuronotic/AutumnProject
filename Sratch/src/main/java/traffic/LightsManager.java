package traffic;

import java.util.List;

public interface LightsManager {

	void addIncomingLink(Link link);

	boolean isGreen(Link link);

	void setRed(Link link);
	void setGreen(Link link);

	void setAllRed();

	List<Link> linksInOrderAdded();

}
