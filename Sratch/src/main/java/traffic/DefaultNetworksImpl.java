package traffic;

import static traffic.SimulationTime.*;

import com.google.inject.Inject;

public class DefaultNetworksImpl implements DefaultNetworks {

	private final JunctionBuilder junctionBuilder;
	private final NetworkBuilderFactory networkBuilderFactory;
	private final LinkBuilderFactory linkBuilderFactory;
	@Inject TimeKeeper timeKeeper;
	
	@Inject DefaultNetworksImpl(
			final JunctionBuilder junctionBuilder,
			final LinkBuilderFactory linkBuilderFactory,
			final NetworkBuilderFactory networkBuilderFactory) {
		this.junctionBuilder = junctionBuilder;
		this.linkBuilderFactory = linkBuilderFactory;
		this.networkBuilderFactory = networkBuilderFactory;
	}

	@Override
	public Network vNetwork2Link(final JunctionController junctionController) {
		final Junction junction0, junction1, junction2;
		junction0 = junctionBuilder
				.withName("junction0")
				.make();
		junction2 = junctionBuilder
				.withName("junction2")
				.make();
		junction1 = junctionBuilder
				.withController(junctionController)
				.withName("junction1")
				.make();


		final int linkLength = 20;

		return networkBuilder()
			.withLink( link()
				.withName("link0")
				.withInJunction(junction0)
				.withOutJunction(junction1)
				.withLength(linkLength)
				.make())
			.withLink( link()
				.withName("link1")
				.withInJunction(junction2)
				.withOutJunction(junction1)
				.withLength(linkLength)
				.make())

			.make();
	}

	@Override
	public Network xNetwork4Link(final JunctionController junctionController) {
		final Junction junction0, junction1, junction2, junction3, junction4;
		junction0 = junctionBuilder.withName("junction0").make();
		junction2 = junctionBuilder.withName("junction2").make();
		junction3 = junctionBuilder.withName("junction3").make();
		junction4 = junctionBuilder.withName("junction4").make();

		junction1 = junctionBuilder
				.withController(junctionController)
				.withName("junction1")
				.make();


		final int linkLength = 20;

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


	@Override
	public Network yNetwork3Link(final JunctionController junctionController) {
		final Junction junction0, junction1, junction2, junction3;
		junction0 = junctionBuilder.withName("junction0").make();
		junction2 = junctionBuilder.withName("junction2").make();
		junction3 = junctionBuilder.withName("junction3").make();
		//junction4 = junctionBuilder.withName("junction4").make();

		junction1 = junctionBuilder
				.withController(junctionController)
				.withName("junction1")
				.make();


		final int linkLength = 20;

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
			.make();
	}

	private NetworkBuilder networkBuilder() {
		return networkBuilderFactory.create();
	}

	private LinkBuilder link() {
		return linkBuilderFactory.create();
	}
	private	JunctionController periodicDutyCycleController() { return new JunctionControllerImpl(timeKeeper, new PeriodicDutyCycleStrategy(), time(5)); }
}
