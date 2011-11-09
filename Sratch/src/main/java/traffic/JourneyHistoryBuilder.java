package traffic;

import java.util.List;

public interface JourneyHistoryBuilder {

	JourneyHistoryBuilder withVehicle(Vehicle vehicle0);
	JourneyHistoryBuilder withStartTime(SimulationTime time);
	JourneyHistoryBuilder withEndTime(SimulationTime time);
	JourneyHistoryBuilder withCellEntryTime(Cell cell, SimulationTime time);

	JourneyHistory make();
	SimulationTime journeyTime();
	void stepped();
	SimulationTime startTime();
	void cellEntered(Cell cell0);
	List<CellTime> cellEntryTimes();
	void noteEndTime();
	SimulationTime endTime();
	Vehicle vehicle();

}