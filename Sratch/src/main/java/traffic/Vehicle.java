package traffic;


public interface Vehicle {
	Cell location();

	void step();

	int journeyTime();

	String name();

	void addJourneyEndListener(JourneyEndListener journeyEndListener);

	void journeyEnded();
}
