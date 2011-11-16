package traffic;

public interface JunctionControllerStrategy {

	void step(LightsManager lightsManager);

	void addIncomingLink(Link link);

}
