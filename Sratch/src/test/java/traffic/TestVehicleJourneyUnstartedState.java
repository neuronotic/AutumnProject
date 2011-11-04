package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestVehicleJourneyUnstartedState {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final VehicleStateFactory vehicleStateFactory = context.mock(VehicleStateFactory.class);
	private final VehicleJourneyState state0 = context.mock(VehicleJourneyState.class, "state0");
	private final VehicleJourneyState state1 = context.mock(VehicleJourneyState.class, "state1");
	private final VehicleStateContext stateContext = context.mock(VehicleStateContext.class);
	private final Vehicle vehicle = context.mock(Vehicle.class);
	final VehicleJourneyUnstartedState vehicleJourneyUnstartedState = new VehicleJourneyUnstartedState(vehicleStateFactory);

	@Test
	public void stepCallsStepOnVehicleJourneyStateReturnedByVehicleStateFactory() throws Exception {

		context.checking(new Expectations() {
			{
				oneOf(vehicleStateFactory).duringJourneyState(); will(returnValue(state0));
				oneOf(state0).step(vehicle, stateContext); will(returnValue(state1));
			}
		});

		assertThat(vehicleJourneyUnstartedState.step(vehicle, stateContext), is(state1));
	}
}
