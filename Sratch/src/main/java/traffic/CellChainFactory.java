package traffic;

import java.util.List;

public interface CellChainFactory {
	CellChain createCellChain(List<Cell> cells);
}
