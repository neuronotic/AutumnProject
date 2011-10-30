package traffic;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

class CellChainBuilderImpl implements CellChainBuilder {
	private final CellFactory cellFactory;
	private final CellChainFactory cellChainFactory;

	private int cellCount;

	@Inject CellChainBuilderImpl(
			final CellChainFactory cellChainFactory,
			final CellFactory cellFactory) {
		this.cellChainFactory = cellChainFactory;
		this.cellFactory = cellFactory;
	}

	public CellChainBuilder cellChainOfLength(final int cellCount) {
		this.cellCount = cellCount;
		return this;
	}

	@Override
	public CellChain make(final Segment segment) {
		final List<Cell> cells = new ArrayList<Cell>();
		for (int i = 0; i < cellCount; i++) {
			cells.add(cellFactory.createCell(segment, i));
		}
		return cellChainFactory.createCellChain(cells);
	}
}
