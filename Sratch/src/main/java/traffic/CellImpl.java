package traffic;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

class CellImpl implements Cell {

	private final Segment segment;
	private final int index;

	@Inject CellImpl(
			@Assisted final Segment segment,
			@Assisted final int index) {
		this.segment = segment;
		this.index = index;
	}

	@Override
	public void enter(final Vehicle vehicle) {

	}

	@Override
	public String toString() {
		//return reflectionToString(this, roadNetworkToStringStyle());
		return name();
	}

	@Override
	public String name() {
		return String.format("%s[%s]", segment.name(), index);
	}
}
