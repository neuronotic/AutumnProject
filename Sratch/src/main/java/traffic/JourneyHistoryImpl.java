package traffic;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class JourneyHistoryImpl implements JourneyHistory {

	private final Vehicle vehicle;
	private final SimulationTime startTime;
	private final SimulationTime endTime;
	private final List<CellTime> cellEntryTimes;

	@Inject JourneyHistoryImpl(
			@Assisted final Vehicle vehicle,
			@Assisted("startTime") final SimulationTime startTime,
			@Assisted final List<CellTime> cellEntryTimes,
			@Assisted("endTime") final SimulationTime endTime) {
				this.vehicle = vehicle;
				this.startTime = startTime;
				this.cellEntryTimes = cellEntryTimes;
				this.endTime = endTime;

	}

	@Override
	public boolean equals(final Object that) {
		if (that != null && that.getClass().equals(this.getClass())) {
			final JourneyHistoryImpl thatJourneyHistory = (JourneyHistoryImpl) that;
			return new EqualsBuilder()
				.append(vehicle, thatJourneyHistory.vehicle)
				.append(startTime, thatJourneyHistory.startTime)
				.append(endTime, thatJourneyHistory.endTime)
				.append(cellEntryTimes, thatJourneyHistory.cellEntryTimes)
				.isEquals();
		} return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(vehicle)
			.append(startTime)
			.append(cellEntryTimes)
			.append(endTime)
			.toHashCode();
	}

	@Override
	public String toString() {
		return String.format("Journey hisitem.statistics().getEndedJourneyHistories().size() ==  expectedcount;tory for %s, with startTime %s and finishTime %s and cellTimes %s", vehicle.name(), startTime, endTime, cellEntryTimes);
	}

	@Override
	public Flow flow() {
		return vehicle.flow();
	}

	@Override
	public SimulationTime journeyDuration() {
		return endTime.differenceBetween(startTime);
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
