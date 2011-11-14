package traffic;

import static com.google.common.collect.Sets.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.inject.Inject;

public class NetworkFluxBuilderImpl implements NetworkFluxBuilder {

	private final NetworkFluxFactory networkFluxFactory;
	private final Map<Link, LinkFlux> linkFluxes = new HashMap<Link, LinkFlux>();
	private final LinkFluxFactory linkFluxFactory;

	@Inject public NetworkFluxBuilderImpl(
			final NetworkFluxFactory networkFluxFactory,
			final LinkFluxFactory linkFluxFactory) {
		this.networkFluxFactory = networkFluxFactory;
		this.linkFluxFactory = linkFluxFactory;
	}

	@Override
	public NetworkFlux make() {
		return networkFluxFactory.create(getLinkFluxes());
	}

	@Override
	public NetworkFluxBuilder withLinkFlux(final LinkFlux linkFlux) {
		linkFluxes.put(linkFlux.link(), linkFlux);
		return this;
	}

	@Override
	public Set<LinkFlux> getLinkFluxes() {
		return newHashSet(linkFluxes.values());
	}

	@Override
	public NetworkFluxBuilder forNetwork(final Network network) {
		for (final Link link : network.links()) {
			withLinkFlux(linkFluxFactory.create(link, 0));
		}
		return this;
	}

	@Override
	public NetworkFluxBuilder copying(final NetworkFluxBuilder networkFluxBuilder) {
		for (final LinkFlux linkFlux : networkFluxBuilder.getLinkFluxes()) {
			linkFluxes.put(linkFlux.link(), linkFlux);
		}
		return this;
	}
}
