package traffic;

import com.google.inject.Inject;

public class DefaultNetworksImpl implements DefaultNetworks {

	private final JunctionFactory junctionFactory;
	private final NetworkBuilderFactory networkBuilderFactory;
	private final LinkBuilderFactory linkBuilderFactory;

	@Inject DefaultNetworksImpl(final JunctionFactory junctionFactory, final LinkBuilderFactory linkBuilderFactory, final NetworkBuilderFactory networkBuilderFactory) {
		this.junctionFactory = junctionFactory;
		this.linkBuilderFactory = linkBuilderFactory;
		this.networkBuilderFactory = networkBuilderFactory;
	}

	@Override
	public Network reverseE() {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public Network xNetwork4Link() {
		Junction junction0, junction1, junction2, junction3, junction4;
		junction0 = junctionFactory.createJunction("junction0");
		junction1 = junctionFactory.createJunction("junction1");
		junction2 = junctionFactory.createJunction("junction2");
		junction3 = junctionFactory.createJunction("junction3");
		junction4 = junctionFactory.createJunction("junction4");

		final int linkLength = 5;

		return networkBuilder()
			.withLink( link()
				.withName("link0")
				.withInJunction(junction0)
				.withOutJunction(junction1)
				.withLength(linkLength)
				.make())
			.withLink( link()
				.withName("link1")
				.withInJunction(junction1)
				.withOutJunction(junction2)
				.withLength(linkLength)
				.make())
			.withLink( link()
				.withName("link2")
				.withInJunction(junction3)
				.withOutJunction(junction1)
				.withLength(linkLength)
				.make())
			.withLink( link()
				.withName("link3")
				.withInJunction(junction1)
				.withOutJunction(junction4)
				.withLength(linkLength)
				.make())
			.make();
	}

	private NetworkBuilder networkBuilder() {
		return networkBuilderFactory.create();
	}

	private LinkBuilder link() {
		return linkBuilderFactory.create();
	}

}
