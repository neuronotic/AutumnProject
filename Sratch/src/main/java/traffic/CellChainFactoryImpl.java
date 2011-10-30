package traffic;

import java.util.ArrayList;
import java.util.List;

final class CellChainFactoryImpl implements CellChainFactory {
	private final int cellCount;

	CellChainFactoryImpl(int cellCount) {
		this.cellCount = cellCount;
	}

	@Override
	public CellChain make(final Segment segment) {
		final List<Cell> cells = new ArrayList<Cell>();
		for (int i = 0; i < cellCount; i++) {
			cells.add(new CellImpl(null, 0));
		}
		return new CellChainImpl(cells);
	}
}