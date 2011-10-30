package traffic;

public class CellChainBuilderImpl implements CellChainBuilder {
	public CellChainFactory cellChainOfLength(final int cellCount) {
		return new CellChainFactoryImpl(cellCount);
	}
}
