package traffic;

import static traffic.TrafficToStringStyle.*;

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
		return networkReflectionToString(this);
	}

	@Override
	public Cell getCellAtIndex(final int index) {
		return cells.get(index);
	}

	@Override
	public Cell lastCell() {
		return cells.get(cells.size() - 1);
	}

	@Override
	public int occupancy() {
		int occupiedCount = 0;
		for (final Cell cell : cells) {
			occupiedCount += cell.isOccupied() ? 1 : 0;
		}
		return occupiedCount;
	}
}
