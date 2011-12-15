package traffic;

import com.google.inject.Inject;

public class SinusoidalTemporalPattern implements TemporalPattern {

	private final SimulationTime period;
	private final TimeKeeper timeKeeper;


	public SinusoidalTemporalPattern(
			final TimeKeeper timeKeeper,
			final SimulationTime period
			) {
				this.timeKeeper = timeKeeper;
				this.period = period;
	}

	@Override
	@Inject public double modifier() {
		//amplitude * abs(sin[(time+phase) * 2 * pi / period])
		final int time = timeKeeper.currentTime().value();
		final double timeAngle = Math.PI * (time % period.value()) / period.value();

		return Math.abs(Math.sin(timeAngle));
		//return min + (max-min) * 0.5 * (1 + Math.abs(Math.sin(2*Math.PI / period.value() * (time + phase.value()))));
	}

}
