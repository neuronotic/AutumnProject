package traffic;

import static com.google.common.collect.Sets.*;
import static java.util.Arrays.*;
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
	private final JunctionOccupancy junctionOccupancy0 =
			context.mock(JunctionOccupancy.class, "junctionOccupancy0");
	private final JunctionOccupancy junctionOccupancy1 =
			context.mock(JunctionOccupancy.class, "junctionOccupancy1");
	private final NetworkOccupancy networkOccupancy = context.mock(NetworkOccupancy.class);
	private final NetworkOccupancyFactory networkOccupancyFactory
		= context.mock(NetworkOccupancyFactory.class);

	@Test
	public void occupancyCallsFactoryToCreate() throws Exception {
		final Network network = createOneLinkNetworkAndConstructorExpectations();
		context.checking(new Expectations() {
			{
				oneOf(junction0).occupancy(); will(returnValue(junctionOccupancy0));
				oneOf(junction1).occupancy(); will(returnValue(junctionOccupancy1));
				oneOf(networkOccupancyFactory).create(newHashSet(junctionOccupancy0, junctionOccupancy1));
					will(returnValue(networkOccupancy));
			}

		});
		assertThat(network.occupancy(), is(networkOccupancy));
	}

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
		assertThat(new NetworkImpl(networkOccupancyFactory, asList(link)).links(), contains(link));
	}

	private Network createOneLinkNetworkAndConstructorExpectations() {
		context.checking(new Expectations() {
			{
				allowing(link).name(); will(returnValue("link"));
				oneOf(link).inJunction(); will(returnValue(junction0));
				oneOf(link).outJunction(); will(returnValue(junction1));
			}
		});
		return new NetworkImpl(networkOccupancyFactory, asList(link));
	}
}
