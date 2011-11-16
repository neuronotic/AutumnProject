package traffic;

import java.util.List;

import com.google.inject.assistedinject.Assisted;

public class MaskedBinaryTemporalPattern implements TemporalPattern {

	private final List<Integer> mask;
	private final TimeKeeper timeKeeper;

	public MaskedBinaryTemporalPattern(final TimeKeeper timeKeeper, @Assisted final List<Integer> mask) {
		this.timeKeeper = timeKeeper;
		this.mask = mask;
	}

	@Override
	public double modifier() {
		final int timestep = timeKeeper.currentTime().value();
		return mask.get(timestep % mask.size());
	}

}
