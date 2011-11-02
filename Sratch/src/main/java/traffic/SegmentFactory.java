package traffic;

import com.google.inject.assistedinject.Assisted;

public interface SegmentFactory {
	Segment createSegment(
			String segmentName,
			@Assisted("inJunction") Junction inJunction,
			CellChainBuilder cellChainBuilder,
			@Assisted("outJunction") Junction outJunction);
}
