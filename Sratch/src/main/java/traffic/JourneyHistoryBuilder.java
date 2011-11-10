package traffic;

import java.util.List;

public interface JourneyHistoryBuilder {
	JourneyHistory make(Vehicle vehicle);

	JourneyHistoryBuilder withStartTime(SimulationTime time);
	JourneyHistoryBuilder withEndTime(SimulationTime time);
	JourneyHistoryBuilder withCellEntryTime(Cell cell, SimulationTime time);

	void cellEntered(Cell cell0);
	void noteEndTime();

	SimulationTime startTime();
	List<CellTime> cellEntryTimes();
	SimulationTime endTime();

	SimulationTime journeyTime();
}