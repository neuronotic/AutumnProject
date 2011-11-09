package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;


public class TestVehicleManagerImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final TimeKeeper timeKeeper = context.mock(TimeKeeper.class);
	private final Vehicle vehicle0 = context.mock(Vehicle.class, "vehicle0");
	private final JourneyCompletedMessage journeyCompletedMessage = context.mock(JourneyCompletedMessage.class);
	private final JourneyHistory journeyHistory = context.mock(JourneyHistory.class);

	private final VehicleManager vehicleManager = new VehicleManagerImpl(timeKeeper);

	@Test
	public void journeyCompletedStoresVehiclesJourneyHistoryAndVehicleNoLongerStepped() throws Exception {
		vehicleManager.addVehicle(vehicle0);
		context.checking(new Expectations() {
			{
				oneOf(journeyCompletedMessage).journeyHistory(); will(returnValue(journeyHistory));
				oneOf(journeyCompletedMessage).vehicle(); will(returnValue(vehicle0));
			}
		});
		vehicleManager.journeyCompleted(journeyCompletedMessage);
		assertThat(vehicleManager.getCompletedJourneyHistories(), contains(journeyHistory));

		context.checking(new Expectations() {
			{
				never(vehicle0).step();
				ignoring(timeKeeper);
			}
		});
		vehicleManager.step();
	}

	@Test
	public void timeKeeperSteppedWithEachStep() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(timeKeeper).step();
			}
		});
		vehicleManager.step();

		context.checking(new Expectations() {
			{
				exactly(2).of(timeKeeper).step();
			}
		});
		vehicleManager.step(2);

	}

	@Test
	public void multipleAddedVehiclesAreSteppedByManager() throws Exception {
		final Vehicle vehicle1 = context.mock(Vehicle.class, "vehicle1");

		vehicleManager.addVehicle(vehicle0);
		vehicleManager.addVehicle(vehicle1);

		setVehicleStepExpectation(vehicle0);
		setVehicleStepExpectation(vehicle1);

		vehicleManager.step();
	}


	@Test
	public void managerCanBeSteppedMultipleTimes() throws Exception {
		vehicleManager.addVehicle(vehicle0);

		setVehicleStepExpectation(vehicle0);
		setVehicleStepExpectation(vehicle0);
		setVehicleStepExpectation(vehicle0);

		vehicleManager.step(3);
	}

	private void setVehicleStepExpectation(final Vehicle vehicle) {
		context.checking(new Expectations() {
			{
				oneOf(vehicle).step();
				ignoring(timeKeeper);
			}
		});
	}


}
