package traffic;

public class MyRandomImpl implements MyRandom {

	@Override
	public boolean bernoulli(final double probability) {
		return Math.random() < probability;
	}

}
