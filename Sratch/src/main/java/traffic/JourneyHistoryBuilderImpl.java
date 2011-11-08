package traffic;

import static traffic.RoadNetworkToStringStyle.*;
import static traffic.SimulationTime.*;

public class JourneyHistoryBuilderImpl implements JourneyHistoryBuilder {
	private int journeyTime = 0;


	@Override
	public JourneyHistory make() {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public void stepped() {
		journeyTime++;
		//logger.info(String.format("Step %s", journeyTime));
	}

	@Override
	public SimulationTime journeyTime() {
		return time(journeyTime);
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
	public String toString() {
		return roadNetworkReflectionToString(this);
	}

}
