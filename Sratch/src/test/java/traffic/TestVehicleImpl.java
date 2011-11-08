package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.SimulationTime.*;
import static traffic.VehicleMatchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;


public class TestVehicleImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Cell cell = context.mock(Cell.class, "cell0");
	private final VehicleStateContext stateContext = context.mock(VehicleStateContext.class);
	private final VehicleJourneyState state0 = context.mock(VehicleJourneyState.class, "state0");
	private final VehicleJourneyState state1 = context.mock(VehicleJourneyState.class, "state1");
	final Vehicle vehicle = new VehicleImpl("myVehicle", stateContext, state0);

	@Test
	public void stepCausesCurrentStateToChangeToReturnedStateInDelegationToPreviousCurrentState() throws Exception {
		stepDelegatesCallOnToCurrentState();

		context.checking(new Expectations() {{
			oneOf(state1).step(vehicle, stateContext);
		}});
		vehicle.step();
	}

	@Test
	public void stepDelegatesCallOnToCurrentState() throws Exception {
		context.checking(new Expectations() {{
			ignoring(stateContext);
			oneOf(state0).step(vehicle, stateContext); will(returnValue(state1));
		}});
		vehicle.step();
	}

	@Test
	public void locationDelegatedToStateContext() throws Exception {
		context.checking(new Expectations() {{
			ignoring(state0);
			oneOf(stateContext).location(); will(returnValue(cell));
		}});
		assertThat(vehicle, isLocatedAt(cell));
	}

	@Test
	public void journeyTimeDelegatesRequestToStateContext() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(stateContext).journeyTime(); will(returnValue(time(42)));
			}
		});
		assertThat(vehicle.journeyTime(), equalTo(time(42)));
	}

	@Test
	public void vehicleIsNamed() throws Exception {
		assertThat(vehicle, VehicleMatchers.vehicleIsNamed("myVehicle"));
	}
}
