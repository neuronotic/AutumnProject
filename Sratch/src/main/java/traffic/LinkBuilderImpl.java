package traffic;

import com.google.inject.Inject;

public class LinkBuilderImpl implements LinkBuilder {

	private CellChainBuilder cellChainBuilder;
	private final LinkFactory linkFactory;
	private String linkName;
	private Junction outJunction;
	private Junction inJunction;

	@Inject LinkBuilderImpl(
			final LinkFactory linkFactory,
			final CellChainBuilder cellChainBuilder) {
				this.linkFactory = linkFactory;
				this.cellChainBuilder = cellChainBuilder;
	}

	@Override
	public Link make() {
		final Link link = linkFactory.createLink(linkName, inJunction, cellChainBuilder, outJunction);
		outJunction.addIncomingLinks(link);
		inJunction.addOutgoingLink(link);
		return link;
	}

	@Override
	public LinkBuilder withInJunction(final Junction inJunction) {
		this.inJunction = inJunction;
		return this;
	}

	@Override
	public LinkBuilder withOutJunction(final Junction outJunction) {
		this.outJunction = outJunction;
		return this;
	}

	@Override
	public LinkBuilder withLength(final int linkLength) {
		cellChainBuilder = cellChainBuilder.cellChainOfLength(linkLength);
		return this;
	}

	@Override
	public LinkBuilder withName(final String linkName) {
		this.linkName = linkName;
		return this;
	}
}
