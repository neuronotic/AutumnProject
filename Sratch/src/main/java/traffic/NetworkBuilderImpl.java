package traffic;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

public class NetworkBuilderImpl implements NetworkBuilder {

	private final NetworkFactory networkFactory;
	private final List<LinkBuilder> linkBuilders = new ArrayList<LinkBuilder>();
	private final List<Link> links = new ArrayList<Link>();

	@Inject public NetworkBuilderImpl(final NetworkFactory networkFactory) {
		this.networkFactory = networkFactory;
	}

	@Override
	public Network make() {
		final List<Link> linksToMakeNetworkWith = new ArrayList<Link>(links);
		for (final LinkBuilder builder : linkBuilders) {
			linksToMakeNetworkWith.add(builder.make());
		}
		return networkFactory.createNetwork(linksToMakeNetworkWith);
	}

	@Override
	public NetworkBuilder withLinkBuilder(final LinkBuilder linkBuilder) {
		linkBuilders.add(linkBuilder);
		return this;
	}

	@Override
	public NetworkBuilder withLink(final Link link) {
		links.add(link);
		return this;
	}

}
