package traffic;

public interface LightsManager {

	void addIncomingLink(Link link);

	boolean isGreen(Link link);

	void setRed(Link link);
	void setGreen(Link link);

	void setAllRed();

}
