package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestNetworkImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Link link = context.mock(Link.class);
	private final Junction junction0 = context.mock(Junction.class, "junction0");
	private final Junction junction1 = context.mock(Junction.class, "junction1");

	@Test
	public void stepCallsStepOnAllJunctions() throws Exception {
		final Network network = createOneLinkNetworkAndConstructorExpectations();

		context.checking(new Expectations() {
			{
				oneOf(junction0).step();
				oneOf(junction1).step();
			}
		});

		network.step();
	}

	@Test
	public void junctionsReturnsListOfJunctionsOnNetwork() throws Exception {
		assertThat(createOneLinkNetworkAndConstructorExpectations().junctions(), containsInAnyOrder(junction0, junction1));
	}

	@Test
	public void linksReturnsListOfLinksOnNetwork() throws Exception {
		context.checking(new Expectations() {
			{
				ignoring(link);
			}
		});
		assertThat(new NetworkImpl(link).links(), contains(link));
	}

	private Network createOneLinkNetworkAndConstructorExpectations() {
		context.checking(new Expectations() {
			{
				oneOf(link).inJunction(); will(returnValue(junction0));
				oneOf(link).outJunction(); will(returnValue(junction1));
			}
		});
		return new NetworkImpl(link);
	}
}
