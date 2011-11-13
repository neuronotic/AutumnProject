package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.util.MyCollectionsProcessing.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestNetworkFluxBuilderImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	public final NetworkFluxFactory networkFluxFactory = context.mock(NetworkFluxFactory.class);
	public final NetworkFlux networkFlux = context.mock(NetworkFlux.class);
	public final LinkFlux linkFlux0 = context.mock(LinkFlux.class, "linkFlux0");
	public final LinkFlux linkFlux1 = context.mock(LinkFlux.class, "linkFlux1");

	public final NetworkFluxBuilder networkFluxBuilder = new NetworkFluxBuilderImpl(networkFluxFactory);

	@Test
	public void makeCallsFactoryToBuildFluxWithLinkFluxes() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(networkFluxFactory).create(asSet(linkFlux0, linkFlux1)); will(returnValue(networkFlux));
			}
		});
		networkFluxBuilder
			.withLinkFlux(linkFlux0)
			.withLinkFlux(linkFlux1);
		assertThat(networkFluxBuilder.make(), is(networkFlux));
	}

	@Test
	public void withMethodsReturnInstanceTheyWereInvokedUpon() throws Exception {
		assertThat(networkFluxBuilder.withLinkFlux(null), is(networkFluxBuilder));
	}
}
