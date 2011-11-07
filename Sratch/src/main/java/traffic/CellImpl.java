package traffic;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

class CellImpl implements Cell {

	private final Segment segment;
	private final int index;
	private final String name;
	private boolean occupied = false;

	@Inject CellImpl(
			@Assisted final Segment segment,
			@Assisted final int index) {
		this.segment = segment;
		name = String.format("%s[%s]", segment.name(), index);
		this.index = index;
	}

	@Override
	public boolean enter(final Vehicle vehicle) {
		if (occupied) {
			return false;
		}
		occupied = true;
		return true;
	}

	@Override
	public String toString() {
		//return reflectionToString(this, roadNetworkToStringStyle());
		return name();
	}

	@Override
	public String name() {
		return name;
	}

}
