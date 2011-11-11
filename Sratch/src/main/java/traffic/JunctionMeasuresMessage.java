package traffic;

public interface JunctionMeasuresMessage {

	Junction junction();

	SimulationTime time();
	double congestion();

	int vehiclesWaiting();
}
