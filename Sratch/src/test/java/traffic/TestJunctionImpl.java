package traffic;

import static com.google.common.collect.Sets.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.NetworkMatchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestJunctionImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Vehicle vehicle0 = context.mock(Vehicle.class, "vehicle0");
	private final Vehicle vehicle1 = context.mock(Vehicle.class, "vehicle1");
	private final JunctionOccupancyFactory junctionOccupancyFactory
		= context.mock(JunctionOccupancyFactory.class);
	private final Link link0 = context.mock(Link.class, "link0");
	private final Link link1 = context.mock(Link.class, "link1");
	private final LinkOccupancy linkOccupancy0 = context.mock(LinkOccupancy.class, "linkOccupancy0");
	private final LinkOccupancy linkOccupancy1 = context.mock(LinkOccupancy.class, "linkOccupancy1");
	private final JunctionOccupancy junctionOccupancy = context.mock(JunctionOccupancy.class);
	private final Occupancy occupancy = context.mock(Occupancy.class);
	private final OccupancyFactory occupancyFactory = context.mock(OccupancyFactory.class);
	private final JunctionController junctionController = context.mock(JunctionController.class);
	private final LightsManager lightsManager = context.mock(LightsManager.class);

	private final Junction junction = junction();

	private JunctionImpl junction() {
		return new JunctionImpl(junctionOccupancyFactory, occupancyFactory, lightsManager, "myJunction", junctionController);
	}

	@Test
	public void stepOnlyDelegatedToControllerWhenThereAreIncomingLinks() throws Exception {
		junction.step();
		addIncomingLinkAndSetupExpectations(link1);
		context.checking(new Expectations() {
			{
				oneOf(junctionController).step(lightsManager);
			}
		});
		junction.step();
	}

	@Test
	public void occupancyOnUnoccupiedJunctionConstructsOccupancyObjectForJunctionGathersOccupancyForIncomingLinksAndUsesFactoryToCreateJunctionOccupancy() throws Exception {
		addIncomingLinkAndSetupExpectations(link0);
		addIncomingLinkAndSetupExpectations(link1);
		context.checking(new Expectations() {
			{
				oneOf(link0).occupancy(); will(returnValue(linkOccupancy0));
				oneOf(link1).occupancy(); will(returnValue(linkOccupancy1));
				oneOf(occupancyFactory).create(0,1); will(returnValue(occupancy));
				oneOf(junctionOccupancyFactory).create(junction, occupancy, newHashSet(linkOccupancy0, linkOccupancy1));
					will(returnValue(junctionOccupancy));
			}
		});
		assertThat(junction.occupancy(), is(junctionOccupancy));
	}

	@Test
	public void addedVehiclesArePlacedInOrderOnQueue() throws Exception {
		junction.addVehicle(vehicle0);
		junction.addVehicle(vehicle1);

		assertThat(junction.enter(vehicle1), is(false));
		assertThat(junction.enter(vehicle0), is(true));
		junction.leave();
		assertThat(junction.enter(vehicle1), is(true));
	}

	@Test
	public void isOccupiedReturnsTrueOnlyIfVehicleIsInCell() throws Exception {
		assertThat(junction.isOccupied(), is(false));
		assertThat(junction.enter(vehicle0), is(true));
		assertThat(junction.isOccupied(), is(true));
	}

	@Test
	public void junctionCanOnlyHaveOneOccupantAtATime() throws Exception {
		assertThat(junction.enter(vehicle0), is(true));
		assertThat(junction.enter(vehicle1), is(false));
	}

	@Test
	public void leaveEmptiesJunction() throws Exception {
		junction.enter(vehicle0);
		assertThat(junction.isOccupied(), is(true));
		junction.leave();
		assertThat(junction.isOccupied(), is(false));
	}

	@Test
	public void junctionIsCreatedWithGivenName() {
		assertThat(junction, isJunctionCalled("myJunction"));
	}

	private void addIncomingLinkAndSetupExpectations(final Link link) {
		context.checking(new Expectations() {
			{
				oneOf(lightsManager).addIncomingLink(link);
			}
		});
		junction.addIncomingLink(link);
	}
}
