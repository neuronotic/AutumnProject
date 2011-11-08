package traffic;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

public class JourneyHistoryBuilderImpl implements JourneyHistoryBuilder {



	private final JourneyHistoryFactory journeyHistoryFactory;
	private Vehicle vehicle;
	private SimulationTime startTime, finishTime;
	private final List<CellTime> cellEntryTimes = new ArrayList<CellTime>();

	@Inject public JourneyHistoryBuilderImpl(final JourneyHistoryFactory journeyHistoryFactory) {
		this.journeyHistoryFactory = journeyHistoryFactory;
	}

	@Override
	public JourneyHistory make() {
		return journeyHistoryFactory.create(vehicle, startTime, cellEntryTimes, finishTime);
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
	public JourneyHistoryBuilder withFinishTime(final SimulationTime finishTime) {
		this.finishTime = finishTime;
		return this;
	}

	@Override
	public JourneyHistoryBuilder withCellEntryTime(final Cell cell, final SimulationTime time) {
		cellEntryTimes.add(CellTime.cellTime(cell, time));
		return this;
	}
}
