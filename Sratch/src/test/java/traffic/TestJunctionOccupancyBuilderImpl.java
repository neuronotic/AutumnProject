package traffic;

import static com.google.common.collect.Sets.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestJunctionOccupancyBuilderImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Occupancy occupancy = context.mock(Occupancy.class);
	private final Junction junction = context.mock(Junction.class);
	private final LinkOccupancy linkOccupancy0 = context.mock(LinkOccupancy.class, "linkOccupancy0");
	private final LinkOccupancy linkOccupancy1 = context.mock(LinkOccupancy.class, "linkOccupancy1");
	private final JunctionOccupancyFactory junctionOccupancyFactory = context.mock(JunctionOccupancyFactory.class);
	private final JunctionOccupancy junctionOccupancy = context.mock(JunctionOccupancy.class);

	private final JunctionOccupancyBuilder junctionOccupancyBuilder = new JunctionOccupancyBuilderImpl(junctionOccupancyFactory);

	@Test
	public void makeUtilisesSuppliedDependenciesWhenCallingFactoryToCreate() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(junctionOccupancyFactory).create(junction, occupancy, newHashSet(linkOccupancy0, linkOccupancy1));
					will(returnValue(junctionOccupancy));
			}
		});
		junctionOccupancyBuilder
			.withJunction(junction)
			.withOccupancy(occupancy)
			.withIncomingLinkOccupancy(linkOccupancy0)
			.withIncomingLinkOccupancy(linkOccupancy1);
		assertThat(junctionOccupancyBuilder.make(), is(junctionOccupancy));
	}

	@Test
	public void withMethodsReturnInstanceCalledUpon() throws Exception {
		assertThat(junctionOccupancyBuilder.withIncomingLinkOccupancy(null), is(junctionOccupancyBuilder));
		assertThat(junctionOccupancyBuilder.withJunction(null), is(junctionOccupancyBuilder));
		assertThat(junctionOccupancyBuilder.withOccupancy(occupancy), is(junctionOccupancyBuilder));
	}

}
