package traffic;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class FluxReceiverImpl implements FluxReceiver {


	private final NetworkFluxBuilderFactory networkFluxBuilderFactory;
	private final NetworkFluxBuilder emptyNetworkFluxBuilder;
	private List<LinkFluxMessage> linkFluxMessages = new ArrayList<LinkFluxMessage>();
	private NetworkFlux networkFlux;

	@Inject FluxReceiverImpl(
			@Assisted final Network network,
			final NetworkFluxBuilderFactory networkFluxBuilderFactory ) {
				this.networkFluxBuilderFactory = networkFluxBuilderFactory;
				emptyNetworkFluxBuilder = networkFluxBuilderFactory.create().forNetwork(network);
	}

	@Subscribe
	@Override
	public void receiveLinkFluxMessage(final LinkFluxMessage linkFluxMessage) {
		linkFluxMessages.add(linkFluxMessage);
	}

	@Override
	public void step() {
		final NetworkFluxBuilder networkFluxBuilder = networkFluxBuilderFactory.create().copying(emptyNetworkFluxBuilder);
		for (final LinkFluxMessage linkFluxMessage : linkFluxMessages) {
			networkFluxBuilder.withLinkFlux(linkFluxMessage.linkFlux());
		}
		linkFluxMessages = new ArrayList<LinkFluxMessage>();
		networkFlux = networkFluxBuilder.make();
	}

	@Override
	public NetworkFlux currentNetworkFlux() {
		return networkFlux;
	}

}
