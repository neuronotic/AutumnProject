package traffic;

import java.util.HashSet;
import java.util.Set;

import com.google.inject.Inject;

public class NetworkFluxBuilderImpl implements NetworkFluxBuilder {

	private final NetworkFluxFactory networkFluxFactory;
	private final Set<LinkFlux> linkFluxes = new HashSet<LinkFlux>();

	@Inject public NetworkFluxBuilderImpl(final NetworkFluxFactory networkFluxFactory) {
		this.networkFluxFactory = networkFluxFactory;
	}

	@Override
	public NetworkFlux make() {
		return networkFluxFactory.create(linkFluxes);
	}

	@Override
	public NetworkFluxBuilder withLinkFlux(final LinkFlux linkFlux) {
		linkFluxes.add(linkFlux);
		return this;
	}
}
