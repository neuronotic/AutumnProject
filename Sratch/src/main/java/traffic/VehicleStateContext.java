package traffic;


public interface VehicleStateContext {

	Cell location();
	int journeyTime();
	Cell nextCellInItinerary();

	void setLocation(Cell cell);
	void logStep();
	void stepHistory();

}
