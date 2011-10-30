package traffic;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

public class CellChainBuilderImpl implements CellChainBuilder {
	private int cellCount;
	private final CellFactory cellFactory;

	@Inject public CellChainBuilderImpl(final CellFactory cellFactory) {
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
		return new CellChainImpl(cells);
	}
}
