package traffic;

public interface JourneyHistoryBuilder {

	JourneyHistoryBuilder withVehicle(Vehicle vehicle0);
	JourneyHistoryBuilder withStartTime(int time);
	JourneyHistoryBuilder withFinishTime(int time);
	JourneyHistoryBuilder withCellEntryTime(Cell cell, int time);

	JourneyHistory make();

}