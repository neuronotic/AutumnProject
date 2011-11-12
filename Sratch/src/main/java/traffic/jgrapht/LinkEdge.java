package traffic.jgrapht;

import org.jgrapht.graph.DefaultWeightedEdge;

import traffic.Link;

public class LinkEdge extends DefaultWeightedEdge {
	private final Link link;

	public LinkEdge(final Link link) {
		this.link = link;
	}

	public Link link() {
		return link;
	}
}
