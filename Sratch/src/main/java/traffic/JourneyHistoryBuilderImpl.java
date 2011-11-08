package traffic;

import static traffic.CellTime.*;
import static traffic.RoadNetworkToStringStyle.*;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

public class JourneyHistoryBuilderImpl implements JourneyHistoryBuilder {
	private final SimulationTime startTime;
	private SimulationTime endTime;
	private final List<CellTime> cellEntryTimes = new ArrayList<CellTime>();
	private final TimeKeeper timeKeeper;

	@Inject public JourneyHistoryBuilderImpl(final TimeKeeper timeKeeper) {
		this.timeKeeper = timeKeeper;
		startTime = timeKeeper.currentTime();
	}

	@Override
	public JourneyHistory make() {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public void stepped() {
	}

	@Override
	public SimulationTime journeyTime() {
		return timeKeeper.currentTime().differenceBetween(startTime);
	}

	@Override
	public JourneyHistoryBuilder withVehicle(final Vehicle vehicle) {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public JourneyHistoryBuilder withStartTime(final SimulationTime startTime) {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public JourneyHistoryBuilder withFinishTime(final SimulationTime finishTime) {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public JourneyHistoryBuilder withCellEntryTime(final Cell cell, final SimulationTime time) {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public SimulationTime startTime() {
		return startTime;
	}

	@Override
	public String toString() {
		return roadNetworkReflectionToString(this);
	}

	@Override
	public void cellEntered(final Cell cell) {
		cellEntryTimes.add(cellTime(cell, timeKeeper.currentTime()));
	}

	@Override
	public List<CellTime> cellEntryTimes() {
		return cellEntryTimes;
	}

	@Override
	public void noteEndTime() {
		endTime = timeKeeper.currentTime();
	}

	@Override
	public SimulationTime endTime() {
		return endTime;
	}


}
