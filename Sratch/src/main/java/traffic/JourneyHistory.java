package traffic;

public interface JourneyHistory {

	SimulationTime journeyDuration();

	Flow flow();

	SimulationTime endTime();
}
