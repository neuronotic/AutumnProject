package traffic;

import java.util.List;

public interface TimeSeries {

	void add(double value);

	List<Double> toList();

}
