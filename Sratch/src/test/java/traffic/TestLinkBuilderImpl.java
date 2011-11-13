package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestLinkBuilderImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Junction inJunction = context.mock(Junction.class, "inJunction");
	private final Junction outJunction = context.mock(Junction.class, "outJunction");
	private final LinkFactory linkFactory = context.mock(LinkFactory.class);
	private final CellChainBuilder cellChainBuilder = context.mock(CellChainBuilder.class);
	private final Link link = context.mock(Link.class);

	private final int linkLength = 5;
	private final String linkName = "link0";

	@Test
	public void buildsLinkObjectWithSuppliedNameInOutJunctionAndLength() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(linkFactory).createLink(linkName, inJunction, cellChainBuilder, outJunction); will(returnValue(link));
				oneOf(cellChainBuilder).cellChainOfLength(linkLength); will(returnValue(cellChainBuilder));
				oneOf(inJunction).addOutgoingLink(link);
				oneOf(outJunction).addIncomingLink(link);
			}
		});

		final LinkBuilder linkBuilder = new LinkBuilderImpl(linkFactory, cellChainBuilder)
			.withName(linkName)
			.withInJunction(inJunction)
			.withOutJunction(outJunction)
			.withLength(linkLength);

		assertThat(linkBuilder.make(), equalTo(link));
	}

}
