package traffic;

public class LinkFluxMessageImpl implements LinkFluxMessage {

	private final Link link;
	private final LinkFlux linkFlux;

	LinkFluxMessageImpl(final Link link, final LinkFlux linkFlux) {
		this.link = link;
		this.linkFlux = linkFlux;
	}

	@Override
	public LinkFlux linkFlux() {
		return linkFlux;
	}

}
