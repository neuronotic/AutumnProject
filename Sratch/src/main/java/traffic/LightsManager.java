package traffic;

public interface LightsManager {

	void addIncomingLink(Link link);

	boolean isGreen(Link link);

}
