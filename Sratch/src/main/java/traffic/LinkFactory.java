package traffic;

import com.google.inject.assistedinject.Assisted;

public interface LinkFactory {
	Link createLink(
			String linkName,
			@Assisted("inJunction") Junction inJunction,
			CellChainBuilder cellChainBuilder,
			@Assisted("outJunction") Junction outJunction);
}
