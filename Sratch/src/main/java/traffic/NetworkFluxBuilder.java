package traffic;

public interface NetworkFluxBuilder {

	NetworkFluxBuilder withLinkFlux(LinkFlux linkFlux);

	NetworkFlux make();

}
