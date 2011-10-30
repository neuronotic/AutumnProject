package traffic;

import java.util.ArrayList;
import java.util.List;

public class CellChainBuilderImpl implements CellChainBuilder {
	private int cellCount;

	public CellChainBuilder cellChainOfLength(final int cellCount) {
		this.cellCount = cellCount;
		return this;
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
