package traffic;

import java.util.List;

public interface JourneyHistoryBuilder {

	JourneyHistoryBuilder withStartTime(SimulationTime time);
	JourneyHistoryBuilder withEndTime(SimulationTime time);
	JourneyHistoryBuilder withCellEntryTime(Cell cell, SimulationTime time);

	JourneyHistory make(Vehicle vehicle);
	SimulationTime journeyTime();
	SimulationTime startTime();
	void cellEntered(Cell cell0);
	List<CellTime> cellEntryTimes();
	void noteEndTime();
	SimulationTime endTime();

}