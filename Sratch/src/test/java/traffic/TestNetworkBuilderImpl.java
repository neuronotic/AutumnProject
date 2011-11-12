package traffic;
import static java.util.Arrays.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;


public class TestNetworkBuilderImpl{
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final LinkBuilder linkBuilder0 = context.mock(LinkBuilder.class, "linkBuilder0");
	private final LinkBuilder linkBuilder1 = context.mock(LinkBuilder.class, "linkBuilder1");
	private final Link link0 = context.mock(Link.class, "link0");
	private final Link link1 = context.mock(Link.class, "link1");
	private final Link link2 = context.mock(Link.class, "link2");
	private final NetworkFactory networkFactory = context.mock(NetworkFactory.class);
	private final Network network = context.mock(Network.class);

	@Test
	public void networkCreatedWithLinksSuppliedToBuilder() throws Exception {

		context.checking(new Expectations() {
			{
				oneOf(linkBuilder0).make(); will(returnValue(link0));
				oneOf(linkBuilder1).make(); will(returnValue(link1));
				oneOf(networkFactory).createNetwork(asList(link2, link0, link1)); will(returnValue(network));
			}
		});

		final NetworkBuilder networkBuilder = new NetworkBuilderImpl(networkFactory)
			.withLinkBuilder(linkBuilder0)
			.withLinkBuilder(linkBuilder1)
			.withLink(link2);
		assertThat(networkBuilder.make(), equalTo(network));
	}


}
