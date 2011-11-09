package traffic;

import static traffic.CellTime.*;
import static traffic.RoadNetworkToStringStyle.*;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

public class JourneyHistoryBuilderImpl implements JourneyHistoryBuilder {
	private SimulationTime startTime;
	private SimulationTime endTime;
	private Vehicle vehicle;
	private final List<CellTime> cellEntryTimes = new ArrayList<CellTime>();
	private final TimeKeeper timeKeeper;
	private final JourneyHistoryFactory journeyHistoryFactory;

	@Inject public JourneyHistoryBuilderImpl(
			final JourneyHistoryFactory journeyHistoryFactory,
			final TimeKeeper timeKeeper) {
		this.journeyHistoryFactory = journeyHistoryFactory;
		this.timeKeeper = timeKeeper;
		withStartTime(timeKeeper.currentTime());
	}

	@Override
	public JourneyHistory make() {
		return journeyHistoryFactory.create(vehicle, startTime, cellEntryTimes, endTime);
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
		this.vehicle = vehicle;
		return this;
	}

	@Override
	public JourneyHistoryBuilder withStartTime(final SimulationTime startTime) {
		this.startTime = startTime;
		return this;
	}

	@Override
	public JourneyHistoryBuilder withEndTime(final SimulationTime endTime) {
		this.endTime = endTime;
		return this;
	}

	@Override
	public JourneyHistoryBuilder withCellEntryTime(final Cell cell, final SimulationTime time) {
		cellEntryTimes.add(cellTime(cell, time));
		return this;
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
		withCellEntryTime(cell, timeKeeper.currentTime());
	}

	@Override
	public List<CellTime> cellEntryTimes() {
		return cellEntryTimes;
	}

	@Override
	public void noteEndTime() {
		withEndTime(timeKeeper.currentTime());
	}

	@Override
	public SimulationTime endTime() {
		return endTime;
	}

	@Override
	public Vehicle vehicle() {
		return vehicle;
	}


}
