package traffic;

public class ConstantTemporalPattern implements TemporalPattern {
	private double modifier;

	public ConstantTemporalPattern(final double modifier) {
		this.modifier = modifier;
	}

	public TemporalPattern withModifier(final double modifier) {
		this.modifier = modifier;
		return this;
	}

	@Override
	public double modifier() {
		return modifier;
	}

}
