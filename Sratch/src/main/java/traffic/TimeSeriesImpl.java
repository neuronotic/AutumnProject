package traffic;

import java.util.ArrayList;
import java.util.List;

public class TimeSeriesImpl implements TimeSeries {

	List<Double> values = new ArrayList<Double>();

	@Override
	public void add(final double value) {
		values.add(value);
	}

	@Override
	public List<Double> toList() {
		return values;
	}

}
