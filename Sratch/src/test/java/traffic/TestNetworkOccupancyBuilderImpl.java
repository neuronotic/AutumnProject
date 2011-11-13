package traffic;

import static java.util.Arrays.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.HashSet;
import java.util.Set;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestNetworkOccupancyBuilderImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final JunctionOccupancyBuilder junctionOccupancyBuilder0 =
			context.mock(JunctionOccupancyBuilder.class, "junctionOccupancyBuilder0");
	private final JunctionOccupancyBuilder junctionOccupancyBuilder1 =
			context.mock(JunctionOccupancyBuilder.class, "junctionOccupancyBuilder1");
	private final JunctionOccupancy junctionOccupancy0 =
			context.mock(JunctionOccupancy.class, "junctionOccupancy0");
	private final JunctionOccupancy junctionOccupancy1 =
			context.mock(JunctionOccupancy.class, "junctionOccupancy1");
	private final NetworkOccupancy networkOccupancy = context.mock(NetworkOccupancy.class);
	private final NetworkOccupancyFactory networkOccupancyFactory = context.mock(NetworkOccupancyFactory.class);

	private final NetworkOccupancyBuilder networkOccupancyBuilder =
			new NetworkOccupancyBuilderImpl(networkOccupancyFactory);

	@Test
	public void make() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(junctionOccupancyBuilder0).make(); will(returnValue(junctionOccupancy0));
				oneOf(junctionOccupancyBuilder1).make(); will(returnValue(junctionOccupancy1));
				oneOf(networkOccupancyFactory).create(asSet(junctionOccupancy0, junctionOccupancy1));
					will(returnValue(networkOccupancy));
			}
		});
		networkOccupancyBuilder
			.withJunctionOccupancy(junctionOccupancyBuilder0)
			.withJunctionOccupancy(junctionOccupancyBuilder1);
		assertThat(networkOccupancyBuilder.make(), is(networkOccupancy));
	}

	@Test
	public void withMethodsReturnInstanceCalledUpon() throws Exception {
		assertThat(networkOccupancyBuilder.withJunctionOccupancy(null), is(networkOccupancyBuilder));
	}

	private Set<JunctionOccupancy> asSet(final JunctionOccupancy...occupancies) {
		return new HashSet<JunctionOccupancy>(asList(occupancies));
	}
}
