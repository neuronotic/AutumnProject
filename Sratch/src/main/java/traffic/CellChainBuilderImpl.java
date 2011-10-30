package traffic;

public class CellChainBuilderImpl implements CellChainBuilder {
	private int cellCount;

	public CellChainBuilder cellChainOfLength(final int cellCount) {
		this.cellCount = cellCount;
		return this;
	}

	@Override
	public CellChain make(final Segment segment) {
		return new CellChainFactoryImpl(cellCount).make(segment);
	}
}
