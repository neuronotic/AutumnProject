package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestRoadNetworkImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Segment segment = context.mock(Segment.class);
	private final Junction junction0 = context.mock(Junction.class, "junction0");
	private final Junction junction1 = context.mock(Junction.class, "junction1");

	@Test
	public void stepCallsStepOnAllJunctions() throws Exception {
		final RoadNetwork roadNetwork = createOneSegmentRoadNetworkAndConstructorExpectations();

		context.checking(new Expectations() {
			{
				oneOf(junction0).step();
				oneOf(junction1).step();
			}
		});

		roadNetwork.step();
	}

	@Test
	public void junctionsReturnsListOfJunctionsOnNetwork() throws Exception {
		assertThat(createOneSegmentRoadNetworkAndConstructorExpectations().junctions(), containsInAnyOrder(junction0, junction1));
	}

	@Test
	public void segmentsReturnsListOfSegmentsOnNetwork() throws Exception {
		context.checking(new Expectations() {
			{
				ignoring(segment);
			}
		});
		assertThat(new RoadNetworkImpl(segment).segments(), contains(segment));
		//assertThat(new RoadNetworkImpl(segment).route(origin, destination), contains(segment));
	}

	private RoadNetwork createOneSegmentRoadNetworkAndConstructorExpectations() {
		context.checking(new Expectations() {
			{
				oneOf(segment).inJunction(); will(returnValue(junction0));
				oneOf(segment).outJunction(); will(returnValue(junction1));
			}
		});
		final RoadNetwork roadNetwork = new RoadNetworkImpl(segment);
		return roadNetwork;
	}
}
