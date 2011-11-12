package traffic;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;

import traffic.jgrapht.LinkEdge;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class ShortestPathRouteFinder implements RouteFinder {


	private final Network network;
	private final DefaultDirectedWeightedGraph<Junction, LinkEdge> graph;

	@Inject
	public ShortestPathRouteFinder(
			@Assisted final Network network) {
		this.network = network;
		graph = convertNetworkToGraph();
	}

	@Override
	public Itinerary calculateItinerary(final Trip trip) {
		final DijkstraShortestPath<Junction, LinkEdge> path = new DijkstraShortestPath<Junction, LinkEdge>(graph, trip.origin(), trip.destination());

		final List<Link> links = new ArrayList<Link>();
		for (final LinkEdge edge : path.getPathEdgeList()) {
			links.add(edge.link());
		}

		return new ItineraryImpl(links);
	}

	private DefaultDirectedWeightedGraph<Junction, LinkEdge> convertNetworkToGraph() {
		final DefaultDirectedWeightedGraph<Junction, LinkEdge> graph = new DefaultDirectedWeightedGraph<Junction, LinkEdge>(LinkEdge.class);
		addJunctionsToGraph(graph);
		addLinksToGraph(graph);
		return graph;
	}

	private void addLinksToGraph(
			final DefaultDirectedWeightedGraph<Junction, LinkEdge> graph) {
		for (final Link link : network.links()) {
			addLinkToGraph(graph, link);
		}
	}

	private void addJunctionsToGraph(
			final DefaultDirectedWeightedGraph<Junction, LinkEdge> graph) {
		for (final Junction junction : network.junctions()) {
			graph.addVertex(junction);
		}
	}

	private void addLinkToGraph(
			final DefaultDirectedWeightedGraph<Junction, LinkEdge> graph,
			final Link link) {
		final LinkEdge edge = new LinkEdge(link);
		graph.addEdge(link.inJunction(), link.outJunction(), edge);
		graph.setEdgeWeight(edge, link.length());
	}
}
