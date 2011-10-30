package traffic;

public interface CellChainBuilder {
	CellChainBuilder cellChainOfLength(int cellCount);
	CellChain make(Segment segment);
}
