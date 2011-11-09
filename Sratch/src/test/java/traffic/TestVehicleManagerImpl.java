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
	private final JourneyEndedMessage journeyEndedMessage = context.mock(JourneyEndedMessage.class);
	private final JourneyHistory journeyHistory = context.mock(JourneyHistory.class);

	private final VehicleManager vehicleManager = new VehicleManagerImpl(timeKeeper);

	@Test
	public void subscribeToJourneyEndNotificationCalledOnAddedVehicle() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(vehicle0).subscribeToJourneyEndNotification(vehicleManager);
			}
		});
		addVehicle(vehicle0);
	}

	@Test
	public void journeyCompletedStoresVehiclesJourneyHistoryAndVehicleNoLongerStepped() throws Exception {
		addVehicle(vehicle0);
		context.checking(new Expectations() {
			{
				oneOf(journeyEndedMessage).journeyHistory(); will(returnValue(journeyHistory));
				oneOf(journeyEndedMessage).vehicle(); will(returnValue(vehicle0));
			}
		});
		vehicleManager.journeyEnded(journeyEndedMessage);
		assertThat(vehicleManager.getEndedJourneyHistories(), contains(journeyHistory));

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

		addVehicle(vehicle0);
		addVehicle(vehicle1);

		setVehicleStepExpectation(vehicle0);
		setVehicleStepExpectation(vehicle1);

		vehicleManager.step();
	}


	@Test
	public void managerCanBeSteppedMultipleTimes() throws Exception {
		addVehicle(vehicle0);

		setVehicleStepExpectation(vehicle0);
		setVehicleStepExpectation(vehicle0);
		setVehicleStepExpectation(vehicle0);

		vehicleManager.step(3);
	}

	private void addVehicle(final Vehicle vehicle) {
		context.checking(new Expectations() {
			{
				allowing(vehicle).subscribeToJourneyEndNotification(vehicleManager);
			}
		});
		vehicleManager.addVehicle(vehicle);
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
