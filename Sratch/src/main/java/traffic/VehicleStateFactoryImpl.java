package traffic;

public class VehicleStateFactoryImpl implements VehicleStateFactory {

	public VehicleJourneyState preJourneyState() {
		return new VehicleJourneyUnstartedState(this);
	}

}
