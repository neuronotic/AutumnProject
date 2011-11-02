package traffic.jgrapht;

import org.jgrapht.graph.DefaultWeightedEdge;

import traffic.Segment;

public class SegmentEdge extends DefaultWeightedEdge {
	private final Segment segment;

	public SegmentEdge(final Segment segment) {
		this.segment = segment;
	}

	public Segment segment() {
		return segment;
	}
}
