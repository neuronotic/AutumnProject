package traffic;

import static java.util.Arrays.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.logging.Logger;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

import com.google.inject.Inject;

public class TestVehicleCreatorImpl {
	@Inject Logger logger = Logger.getAnonymousLogger();

	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final FlowGroup flowGroup0 = context.mock(FlowGroup.class, "flowGroup0");
	private final FlowGroup flowGroup1 = context.mock(FlowGroup.class, "flowGroup1");

	private final TemporalPattern temporalPattern0 = context.mock(TemporalPattern.class, "temporalPattern0");
	private final TemporalPattern temporalPattern1 = context.mock(TemporalPattern.class, "temporalPattern1");

	private final Flow flow0 = context.mock(Flow.class, "flow0");
	private final Flow flow1 = context.mock(Flow.class, "flow1");
	private final Flow flow2 = context.mock(Flow.class, "flow2");

	private final Itinerary itinerary0 = context.mock(Itinerary.class, "itinerary0");
	private final Itinerary itinerary2 = context.mock(Itinerary.class, "itinerary2");

	private final Vehicle vehicle0 = context.mock(Vehicle.class, "vehicle0");
	private final Vehicle vehicle1 = context.mock(Vehicle.class, "vehicle1");

	private final VehicleBuilder vehicleBuilder = context.mock(VehicleBuilder.class);
	private final MyRandom random = context.mock(MyRandom.class);

	private final VehicleCreator vehicleCreator = new VehicleCreatorImpl(random, vehicleBuilder, asList(flowGroup0, flowGroup1));

	@Test
	public void testName() throws Exception {
		setExpectationsOnRandom();
		context.checking(new Expectations() {
			{
				oneOf(flowGroup0).temporalPattern(); will(returnValue(temporalPattern0));
				oneOf(temporalPattern0).modifier();
				oneOf(flowGroup1).temporalPattern(); will(returnValue(temporalPattern1));
				oneOf(temporalPattern1).modifier();
				oneOf(flowGroup0).flows(); will(returnValue(asList(flow0, flow1)));
				oneOf(flowGroup1).flows(); will(returnValue(asList(flow2)));
				oneOf(flow0).itinerary(); will(returnValue(itinerary0));
				oneOf(flow2).itinerary(); will(returnValue(itinerary2));
				oneOf(flow0).probability();
				oneOf(flow1).probability();
				oneOf(flow2).probability();
				oneOf(vehicleBuilder).withItinerary(itinerary0); will(returnValue(vehicleBuilder));
				oneOf(vehicleBuilder).withName("vehicle0"); will(returnValue(vehicleBuilder));
				oneOf(vehicleBuilder).withItinerary(itinerary2); will(returnValue(vehicleBuilder));
				oneOf(vehicleBuilder).withName("vehicle1"); will(returnValue(vehicleBuilder));
				ignoring(itinerary0);
				ignoring(itinerary2);
				exactly(2).of(vehicleBuilder).make(); will(onConsecutiveCalls(returnValue(vehicle0), returnValue(vehicle1)));
			}
		});
		assertThat(vehicleCreator.step().size(), is(2));
	}

	private void setExpectationsOnRandom() {
		context.checking(new Expectations() {
			{
				exactly(3).of(random).bernoulli(with(any(Double.class)));
					will(onConsecutiveCalls(
							returnValue(true),
							returnValue(false),
							returnValue(true)));
			}
		});
	}
}
