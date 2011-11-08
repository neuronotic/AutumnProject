package traffic;

public interface JourneyHistoryBuilder {

	JourneyHistoryBuilder withVehicle(Vehicle vehicle0);
	JourneyHistoryBuilder withStartTime(SimulationTime time);
	JourneyHistoryBuilder withFinishTime(SimulationTime time);
	JourneyHistoryBuilder withCellEntryTime(Cell cell, SimulationTime time);

	JourneyHistory make();

}
