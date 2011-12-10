package traffic;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class CellOccupantDepartedMessageImpl implements CellOccupantDepartedMessage {

	private final Cell cell;
	private final SimulationTime time;

	@Inject CellOccupantDepartedMessageImpl(
			@Assisted final Cell cell,
			final TimeKeeper timeKeeper) {
				this.cell = cell;
				time = timeKeeper.currentTime();
	}

	@Override
	public Cell cell() {
		return cell;
	}

	@Override
	public SimulationTime time() {
		return time;
	}

}
