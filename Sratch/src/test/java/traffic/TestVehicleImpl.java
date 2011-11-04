package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

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

	/*
	@Test
	@Ignore
	public void eachStepAdvancesUserToIterator() throws Exception {
		context.checking(new Expectations() {{
			oneOf(cell).enter(vehicle);
			oneOf(history).stepped();
		}});
		vehicle.step();
		assertThat(vehicle, isLocatedAt(cell));
	}

	@Test
	@Ignore
	public void journeyTimeIsReadFromHistory() throws Exception {
		context.checking(new Expectations() {{
			oneOf(history).journeyTime(); will(returnValue(42));
		}});
		assertThat(vehicle.journeyTime(), equalTo(42));
	}
	*/

	@Test
	public void stepCausesCurrentStateToChangeToReturnedStateInDelegationToPreviousCurrentState() throws Exception {
		stepDelegatesToCurrentState();

		context.checking(new Expectations() {{
			oneOf(state1).step();
		}});
		vehicle.step();
	}

	@Test
	public void stepDelegatesToCurrentState() throws Exception {
		context.checking(new Expectations() {{
			ignoring(stateContext);
			oneOf(state0).step(); will(returnValue(state1));
		}});
		vehicle.step();
	}

	@Test
	public void stepMovesVehicleAlongItinerary() throws Exception {
		context.checking(new Expectations() {{
			ignoring(state0);
			oneOf(stateContext).nextCellInItinerary(); will(returnValue(cell));
			oneOf(cell).enter(vehicle);
			oneOf(stateContext).setLocation(cell);
			oneOf(stateContext).stepHistory();
			oneOf(stateContext).location(); will(returnValue(cell));
		}});
		vehicle.step();
		//assertThat(vehicle, isLocatedAt(cell));
	}

	@Test
	public void journeyTimeDelegatesRequestToStateContext() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(stateContext).journeyTime(); will(returnValue(42));
			}
		});
		assertThat(vehicle.journeyTime(), equalTo(42));
	}

	@Test
	public void vehicleIsNamed() throws Exception {
		assertThat(vehicle, VehicleMatchers.vehicleIsNamed("myVehicle"));
	}
}
