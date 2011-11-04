package traffic;

public interface VehicleStateFactory {
	VehicleJourneyState preJourneyState();

	VehicleJourneyState duringJourneyState();

	VehicleJourneyState postJourneyState();
}
