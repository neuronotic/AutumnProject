package traffic;

//TODO: UNTESTED class....how to test without instanceof? provide factories for each type of state? mmmm search
// for "unit testing factories that return multiple types which have a common supertype"
public class VehicleStateFactoryImpl implements VehicleStateFactory {
	public VehicleJourneyState preJourneyState() {
		return new VehicleJourneyUnstartedState(this);
	}

	@Override
	public VehicleJourneyState duringJourneyState() {
		return new VehicleJourneyStartedState(this);
	}

	@Override
	public VehicleJourneyState postJourneyState() {
		return new VehicleJourneyEndedState();
	}

}
