package traffic.jgrapht;

import org.jgrapht.graph.DefaultDirectedWeightedGraph;

import traffic.Junction;
import traffic.Network;

public interface TrafficNetworkToGraphConverter {

	DefaultDirectedWeightedGraph<Junction, LinkEdge> convert(Network network);

}
