package traffic;

public interface FluxReceiver {

	void step();

	void receiveLinkFluxMessage(LinkFluxMessage linkFluxMessage);

	NetworkFlux currentNetworkFlux();

}
