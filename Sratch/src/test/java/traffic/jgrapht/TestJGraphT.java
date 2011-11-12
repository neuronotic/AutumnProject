package traffic.jgrapht;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.KShortestPaths;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.junit.Rule;
import org.junit.Test;

import traffic.JUnitRuleMockery;
import traffic.Junction;
import traffic.Link;

public class TestJGraphT {

	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	@Test
	public void kShortestPathsWorksWithSimpleNetwork() throws Exception {
		final Junction junction0 = context.mock(Junction.class, "junction0");
		final Junction junction1 = context.mock(Junction.class, "junction1");
		final Junction junction2 = context.mock(Junction.class, "junction2");
		final Junction junction3 = context.mock(Junction.class, "junction3");

		final Link link0 = context.mock(Link.class, "link0");
		final Link link1 = context.mock(Link.class, "link1");
		final Link link2 = context.mock(Link.class, "link2");
		final Link link3 = context.mock(Link.class, "link3");

		final LinkEdge edge0 = new LinkEdge(link0);
		final LinkEdge edge1 = new LinkEdge(link1);
		final LinkEdge edge2 = new LinkEdge(link2);
		final LinkEdge edge3 = new LinkEdge(link3);

		final DefaultDirectedWeightedGraph<Junction, LinkEdge> graph = new DefaultDirectedWeightedGraph<Junction, LinkEdge>(LinkEdge.class);

		graph.addVertex(junction0);
		graph.addVertex(junction1);
		graph.addVertex(junction2);
		graph.addVertex(junction3);

		graph.addEdge(junction0, junction1, edge0);
		graph.setEdgeWeight(edge0, 6);

		graph.addEdge(junction1, junction3, edge1);
		graph.setEdgeWeight(edge0, 3);

		graph.addEdge(junction0, junction2, edge2);
		graph.setEdgeWeight(edge0, 2);

		graph.addEdge(junction2, junction1, edge3);
		graph.setEdgeWeight(edge0, 3);

		final List<GraphPath<Junction, LinkEdge>> paths = new KShortestPaths<Junction, LinkEdge>(graph, junction0, 2).getPaths(junction3);
		assertThat(paths.get(0).getEdgeList(), contains(edge2, edge3, edge1));
		assertThat(paths.get(1).getEdgeList(), contains(edge0, edge1));

		assertThat(edge0.link(), equalTo(link0));
	}

}
