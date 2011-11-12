package traffic;

public interface NetworkBuilder {
	Network make();
	NetworkBuilder withLinkBuilder(LinkBuilder linkBuilder);
	NetworkBuilder withLink(Link link);

}
