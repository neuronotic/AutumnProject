package traffic;
import static java.util.Arrays.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;


public class TestRoadNetworkBuilderImpl{
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final SegmentBuilder segmentBuilder0 = context.mock(SegmentBuilder.class, "segmentBuilder0");
	private final SegmentBuilder segmentBuilder1 = context.mock(SegmentBuilder.class, "segmentBuilder1");
	private final Segment segment0 = context.mock(Segment.class, "segment0");
	private final Segment segment1 = context.mock(Segment.class, "segment1");
	private final Segment segment2 = context.mock(Segment.class, "segment2");
	private final RoadNetworkFactory roadNetworkFactory = context.mock(RoadNetworkFactory.class);
	private final RoadNetwork roadNetwork = context.mock(RoadNetwork.class);

	@Test
	public void roadNetworkCreatedWithSegmentsSuppliedToBuilder() throws Exception {

		context.checking(new Expectations() {
			{
				oneOf(segmentBuilder0).make(); will(returnValue(segment0));
				oneOf(segmentBuilder1).make(); will(returnValue(segment1));
				oneOf(roadNetworkFactory).createRoadNetwork(asList(segment2, segment0, segment1)); will(returnValue(roadNetwork));
			}
		});

		final RoadNetworkBuilder roadNetworkBuilder = new RoadNetworkBuilderImpl(roadNetworkFactory)
			.withSegment(segmentBuilder0)
			.withSegment(segmentBuilder1)
			.withSegment(segment2);
		assertThat(roadNetworkBuilder.make(), equalTo(roadNetwork));
	}


}
