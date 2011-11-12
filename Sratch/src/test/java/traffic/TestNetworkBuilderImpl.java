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

	private final SegmentBuilder segmentBuilder0 = context.mock(SegmentBuilder.class, "segmentBuilder0");
	private final SegmentBuilder segmentBuilder1 = context.mock(SegmentBuilder.class, "segmentBuilder1");
	private final Segment segment0 = context.mock(Segment.class, "segment0");
	private final Segment segment1 = context.mock(Segment.class, "segment1");
	private final Segment segment2 = context.mock(Segment.class, "segment2");
	private final NetworkFactory networkFactory = context.mock(NetworkFactory.class);
	private final Network network = context.mock(Network.class);

	@Test
	public void networkCreatedWithSegmentsSuppliedToBuilder() throws Exception {

		context.checking(new Expectations() {
			{
				oneOf(segmentBuilder0).make(); will(returnValue(segment0));
				oneOf(segmentBuilder1).make(); will(returnValue(segment1));
				oneOf(networkFactory).createNetwork(asList(segment2, segment0, segment1)); will(returnValue(network));
			}
		});

		final NetworkBuilder networkBuilder = new NetworkBuilderImpl(networkFactory)
			.withSegment(segmentBuilder0)
			.withSegment(segmentBuilder1)
			.withSegment(segment2);
		assertThat(networkBuilder.make(), equalTo(network));
	}


}
