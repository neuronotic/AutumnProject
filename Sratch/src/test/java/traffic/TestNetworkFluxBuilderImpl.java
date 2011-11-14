package traffic;

import static com.google.common.collect.Sets.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestNetworkFluxBuilderImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	public final NetworkFluxFactory networkFluxFactory = context.mock(NetworkFluxFactory.class);
	public final LinkFluxFactory linkFluxFactory = context.mock(LinkFluxFactory.class);
	public final NetworkFlux networkFlux = context.mock(NetworkFlux.class);
	public final LinkFlux linkFlux0 = context.mock(LinkFlux.class, "linkFlux0");
	public final LinkFlux linkFlux1 = context.mock(LinkFlux.class, "linkFlux1");
	public final Link link0 = context.mock(Link.class, "link0");
	public final Link link1 = context.mock(Link.class, "link1");
	public final Network network = null;

	public final NetworkFluxBuilder networkFluxBuilder = new NetworkFluxBuilderImpl(networkFluxFactory, linkFluxFactory);

	@Test
	public void makeCallsFactoryToBuildFluxWithLinkFluxes() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(linkFlux0).link(); will(returnValue(link0));
				oneOf(linkFlux1).link(); will(returnValue(link1));
			}
		});
		networkFluxBuilder
			.withLinkFlux(linkFlux0)
			.withLinkFlux(linkFlux1);
		context.checking(new Expectations() {
			{
				oneOf(networkFluxFactory).create(newHashSet(linkFlux0, linkFlux1)); will(returnValue(networkFlux));
			}
		});

		assertThat(networkFluxBuilder.make(), is(networkFlux));
	}

	@Test
	public void withMethodsReturnInstanceTheyWereInvokedUpon() throws Exception {
		context.checking(new Expectations() {
			{
				ignoring(linkFlux0);
			}
		});
		assertThat(networkFluxBuilder.withLinkFlux(linkFlux0), is(networkFluxBuilder));
	}
}
