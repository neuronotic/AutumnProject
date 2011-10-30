package traffic;

import static traffic.RoadNetworkToStringStyle.*;

import java.util.Iterator;
import java.util.List;

public class CellChainImpl implements CellChain {
	private final List<Cell> cells;

	public CellChainImpl(final List<Cell> cells) {
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
