package traffic;


public interface JunctionMeasuresMessageFactory {

	JunctionMeasuresMessage create(Junction junction, double congestion, int vehiclesWaiting);

}
