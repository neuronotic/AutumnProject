package traffic;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static traffic.VehicleManagerFactory.*;

import org.junit.Test;

public class TestVehicleManagerFactory {
	@Test
	public void vehicleManagerCanBeCreated() throws Exception {
		assertThat(vehicleManager(), notNullValue(VehicleManager.class));
	}
}
