package traffic;

import com.google.inject.assistedinject.Assisted;

public interface SegmentFactory {
	Segment segment(
			String segmentName,
			@Assisted("inJunction") Junction inJunction,
			CellChainFactory cellChainFactory,
			@Assisted("outJunction") Junction outJunction);
}
