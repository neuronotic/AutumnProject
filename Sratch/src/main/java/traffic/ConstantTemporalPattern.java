package traffic;

public class ConstantTemporalPattern implements TemporalPattern {
	private final double modifier;

	public ConstantTemporalPattern(final double modifier) {
		this.modifier = modifier;
	}

	@Override
	public double modifier() {
		return modifier;
	}

}
