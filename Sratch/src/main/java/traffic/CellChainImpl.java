package traffic;

import static traffic.RoadNetworkToStringStyle.*;

import java.util.Iterator;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

class CellChainImpl implements CellChain {
	private final List<Cell> cells;

	@Inject
	CellChainImpl(@Assisted final List<Cell> cells) {
		this.cells = cells;
	}

	@Override
	public Iterator<Cell> iterator() {
		return cells.iterator();
	}

	@Override
	public int cellCount() {
		return cells.size();
	}

	@Override
	public String toString() {
		return roadNetworkReflectionToString(this);
	}
}
