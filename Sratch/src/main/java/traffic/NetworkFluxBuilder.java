package traffic;

import java.util.Set;

public interface NetworkFluxBuilder {

	NetworkFluxBuilder withLinkFlux(LinkFlux linkFlux);

	NetworkFlux make();

	Set<LinkFlux> getLinkFluxes();

	NetworkFluxBuilder forNetwork(Network network);
	NetworkFluxBuilder copying(NetworkFluxBuilder networkFluxBuilder);

}
