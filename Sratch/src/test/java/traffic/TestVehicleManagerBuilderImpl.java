package traffic;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestVehicleManagerBuilderImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final VehicleManagerFactory vehicleManagerFactory = context.mock(VehicleManagerFactory.class);
	private final VehicleManager vehicleManager = context.mock(VehicleManager.class);

	@Test
	public void vehicleManagerFactoryCalledToBuildVehicleManagerCanBeCreated() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(vehicleManagerFactory).createVehicleManager(); will(returnValue(vehicleManager));
			}
		});

		assertThat(new VehicleManagerBuilderImpl(vehicleManagerFactory).make(), notNullValue(VehicleManager.class));
	}
}
