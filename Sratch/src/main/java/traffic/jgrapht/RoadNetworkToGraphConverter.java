package traffic.jgrapht;

import org.jgrapht.graph.DefaultDirectedWeightedGraph;

import traffic.Junction;
import traffic.RoadNetwork;

public interface RoadNetworkToGraphConverter {

	DefaultDirectedWeightedGraph<Junction, SegmentEdge> convert(RoadNetwork roadNetwork);

}
