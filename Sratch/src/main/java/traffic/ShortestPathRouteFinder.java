package traffic;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;

import traffic.jgrapht.SegmentEdge;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class ShortestPathRouteFinder implements RouteFinder {


	private final RoadNetwork roadNetwork;
	private final DefaultDirectedWeightedGraph<Junction, SegmentEdge> graph;

	@Inject
	public ShortestPathRouteFinder(
			@Assisted final RoadNetwork roadNetwork) {
		this.roadNetwork = roadNetwork;
		graph = convertRoadNetworkToGraph();
	}

	@Override
	public Itinerary calculateItinerary(final Trip trip) {
		final DijkstraShortestPath<Junction, SegmentEdge> path = new DijkstraShortestPath<Junction, SegmentEdge>(graph, trip.origin(), trip.destination());

		final List<Segment> segments = new ArrayList<Segment>();
		for (final SegmentEdge edge : path.getPathEdgeList()) {
			segments.add(edge.segment());
		}

		return new ItineraryImpl(segments);
	}

	private DefaultDirectedWeightedGraph<Junction, SegmentEdge> convertRoadNetworkToGraph() {
		final DefaultDirectedWeightedGraph<Junction, SegmentEdge> graph = new DefaultDirectedWeightedGraph<Junction, SegmentEdge>(SegmentEdge.class);
		addJunctionsToGraph(graph);
		addSegmentsToGraph(graph);
		return graph;
	}

	private void addSegmentsToGraph(
			final DefaultDirectedWeightedGraph<Junction, SegmentEdge> graph) {
		for (final Segment segment : roadNetwork.segments()) {
			addSegmentToGraph(graph, segment);
		}
	}

	private void addJunctionsToGraph(
			final DefaultDirectedWeightedGraph<Junction, SegmentEdge> graph) {
		for (final Junction junction : roadNetwork.junctions()) {
			graph.addVertex(junction);
		}
	}

	private void addSegmentToGraph(
			final DefaultDirectedWeightedGraph<Junction, SegmentEdge> graph,
			final Segment segment) {
		final SegmentEdge edge = new SegmentEdge(segment);
		graph.addEdge(segment.inJunction(), segment.outJunction(), edge);
		graph.setEdgeWeight(edge, segment.length());
	}
}
